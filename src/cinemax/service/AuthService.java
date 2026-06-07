package service;

import java.util.Objects;

import exception.ParsingException;
import exception.UserNotFoundException;
import model.user.Guest;
import model.user.User;
import repository.CsvProcessor;
import repository.CsvReader;
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

	private final class AccessAuth implements CsvProcessor {
		private String usernameMatch;
		private char[] plainPass;
		public boolean processing;

		private AccessAuth(String userMatch, char[] plainPass) {
			this.usernameMatch = userMatch;
			this.plainPass = plainPass;
			processing = true;
		}

		// file format: username, iterations:hashedPsswd:hashSalt, name, surname,
		// dateOfBirth, domicile, role
		@Override
		public boolean process(String line) {
			String[] sub = line.split(",");

			if (sub.length < 7)
				return false;

			if (this.usernameMatch.equals(sub[0])) {
				return PasswordService.compareCharArrToHash(plainPass, sub[1]);
			}

			return false;
		}
	}

	private final class SignupProcessor implements CsvProcessor {
		public boolean processing;

		SignupProcessor(String username) {

		}

		@Override
		public boolean process(String line) {
			String[] sub = line.split(",");
			// TODO
			return false;
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
	public static boolean signup(String username, String passHash) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(passHash);

		try {
			CsvReader.INSTANCE.setProcessor(INSTANCE.new SignupProcessor(username));
			CsvReader.INSTANCE.process(FilePaths.USERS.getPath());
		} catch (ParsingException e) {
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
	public static SignupValidation validateSignup(String username, char[] plainPassword) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(plainPassword);

		var nameRetrieverProcessor = INSTANCE.new UsernameRetrieverProcessor(username);
		CsvReader.INSTANCE.setProcessor(nameRetrieverProcessor);
		boolean usernameAvailable;
		try {
			usernameAvailable = !CsvReader.INSTANCE.process(FilePaths.USERS.getPath());
		} catch (Exception e) {
			usernameAvailable = false;
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

		for (char c: plainPassword) {
			if (Character.isUpperCase(c)) {
				passwordUppercase = true;
			} else if (Character.isDigit(c)) {
				passwordDigit = true;
			}

			if (c < 'A' || c > 'z') {
				if (!(allowedSpecialChars.indexOf(c) >= 0))
					passwordOnlyAllowedSpecial = false;
				else {
					passwordSpecial = true;
				}
			}
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

	public static boolean login(String username, char[] password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		AccessAuth retriever = INSTANCE.new AccessAuth(username, password);

		CsvReader.INSTANCE.setProcessor(retriever);

		try {
			boolean result = CsvReader.INSTANCE.process(FilePaths.USERS.getPath());
			return result;
		} catch (ParsingException e) {
			return false;
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
