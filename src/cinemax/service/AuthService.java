package service;

import java.util.Objects;

import exception.ParsingException;
import exception.PersistenceException;
import exception.UserNotFoundException;
import exception.ValidationException;
import model.ValidationError;
import model.user.Guest;
import model.user.Member;
import model.user.Projectionist;
import model.user.TicketsClerk;
import model.user.User;
import repository.CsvProcessor;
import repository.CsvReader;
import repository.CsvWriter;
import repository.FilePaths;

/**
 * Class dedicated to authenticating users and ensuring that maximum 1
 * authenticated user exists at all times.
 */
public enum AuthService {
	INSTANCE;

	private static User user;

	AuthService() {
	}

	/**
	 * Representation of a login.
	 */
	private record LoginResult(boolean successful, String name, String surname, User user) {
	}

	private final class AccessAuth implements CsvProcessor {
		private String usernameMatch;
		private char[] plainPass;
		public boolean processing;
		private String retrieved;

		private AccessAuth(String userMatch, char[] plainPass) {
			this.usernameMatch = userMatch;
			this.plainPass = plainPass;
			processing = true;
		}

		// file format: username, hashedPassword, name, surname,
		// dateOfBirth, domicile, role
		@Override
		public boolean process(String line) {
			String[] sub = line.split(",");

			// trying to prevent nasty bugs
			if (sub.length < 7)
				return false;

			if (this.usernameMatch.equals(sub[0])) {
				if (PasswordService.compareCharArrToHash(plainPass, sub[1])) {
					this.retrieved = line;
					return true;
				}
			}

			return false;
		}

		/**
		 * Tries to create and return a login result, starting from the field retrieved.
		 * It does not check whether retrieved is safe to access nor valid format.
		 * Whether a LoginResult object is necessary and the retrieved line isn't
		 * available, it's deemed opportune to create one manually and assign the field
		 * *successful* to false. In that case, the other fields should not be accessed
		 * at all from anywhere, to avoid bugs or exceptions.
		 * 
		 * @return LoginResult object
		 */
		public LoginResult getLoginResult() {
			String[] splitLine = this.retrieved.split(",");
			var name = splitLine[2];
			var surname = splitLine[3];
			var role = splitLine[6];
			User user;

			switch (role) {
				case "Member" -> user = new Member();
				case "TicketsClerk" -> user = new TicketsClerk();
				case "Projectionist" -> user = new Projectionist();
				default -> user = null; // why would this even happen if everything is done right? I guess because it'd
										// be done wrong.
			}

			return new LoginResult(true, name, surname, user);
		}
	}

	private final class UsernameRetrieverProcessor implements CsvProcessor {
		public boolean processing;
		private String username;

		UsernameRetrieverProcessor(String username) {
			this.username = username;
		}

		@Override
		public boolean process(String line) {
			String[] sub = line.split(",");

			if (sub[0].equals(username))
				return true;

			return false;
		}
	}

	/**
	 * Signs up an already validated user. Does not validate any further.
	 * 
	 * @param username username of the user
	 * @param passHash hash of the password of the user
	 * @return success of the operation
	 */
	public static boolean signup(String username, String passHash, String name, String surname, String dateOfBirth,
			String domicile) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(passHash);
		Objects.requireNonNull(name);
		Objects.requireNonNull(surname);
		Objects.requireNonNull(dateOfBirth);
		Objects.requireNonNull(domicile);

		String lineEntry = String.join(",", username + passHash + name + surname + dateOfBirth + domicile + "Member");

		try {
			CsvWriter.INSTANCE.append(FilePaths.USERS.getPath(), lineEntry);
		} catch (PersistenceException e) {
			return false;
		}

		return true;
	}

	public static String getAllowedSpecialCharacter() {
		return "!_$?#(^)-/*+";
	}

	/**
	 * Verifies whether username and password are available and valid.
	 * 
	 * @param username      username to validate and check availability
	 * @param plainPassword plain text password to validate
	 * @return object giving infos about validity and availability
	 */
	public static SignupValidation validateSignup(String username, char[] plainPassword) throws ValidationException {
		Objects.requireNonNull(username);
		Objects.requireNonNull(plainPassword);

		boolean usernameAvailable;
		var nameRetrieverProcessor = INSTANCE.new UsernameRetrieverProcessor(username);
		CsvReader.INSTANCE.setProcessor(nameRetrieverProcessor);
		try {
			usernameAvailable = !CsvReader.INSTANCE.process(FilePaths.USERS.getPath());
		} catch (Exception e) {
			throw new ValidationException(ValidationError.USER_DATABASE_LOOKUP_FAILED);
		}

		// first char can't be a number; the rest must be either a letter or a number
		boolean usernameValid = true;
		if (Character.isDigit(username.charAt(0)))
			usernameValid = false;
		if (usernameValid) {
			for (char c : username.toCharArray()) {
				if (!(Character.isDigit(c) || Character.isLetter(c)))
					usernameValid = false;
			}
		}

		boolean passwordLength = plainPassword != null && plainPassword.length >= 7;
		boolean passwordUppercase = false;
		boolean passwordDigit = false;
		boolean passwordSpecial = false;
		boolean passwordOnlyAllowedSpecial = true;

		String allowedSpecialChars = getAllowedSpecialCharacter();

		for (char c : plainPassword) {
			if (Character.isLetter(c)) {
				if (Character.isUpperCase(c)) {
					passwordUppercase = true;
					continue;
				}

				continue;
			}

			if (Character.isDigit(c)) {
				passwordDigit = true;
				continue;
			}

			if (!(allowedSpecialChars.indexOf(c) >= 0)) {
				passwordOnlyAllowedSpecial = false;
				continue;
			}

			passwordSpecial = true;
		}

		return new SignupValidation(
				usernameAvailable,
				usernameValid,
				passwordLength,
				passwordUppercase,
				passwordDigit,
				passwordSpecial,
				passwordOnlyAllowedSpecial);
	}

	/**
	 * Tries to complete a user login. Returns the representation of the login
	 * resultance.
	 * 
	 * @param username username of the user
	 * @param password password array
	 * @return LoginResult object representing the status of the login. If
	 *         successful is false, other fields should not be accessed.
	 */
	public static LoginResult login(String username, char[] password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		AccessAuth retriever = INSTANCE.new AccessAuth(username, password);

		CsvReader.INSTANCE.setProcessor(retriever);

		try {
			boolean success = CsvReader.INSTANCE.process(FilePaths.USERS.getPath());
			if (success) {
				return retriever.getLoginResult();
			}

			return new LoginResult(false, null, null, null);
		} catch (ParsingException e) {
			return new LoginResult(false, null, null, null);
		}
	}

	public User getUser() throws UserNotFoundException {
		// Used to find eventual bugs in the code. User should exist at all times, after
		// assignment in
		// classpath root main method
		if (user == null)
			throw new UserNotFoundException();

		return user;
	}

	public void setUser(User newUser) {
		user = newUser;
	}

	/**
	 * Equivalent to setUser( new Guest() ).
	 */
	public void logout() {
		user = new Guest();
	}
}
