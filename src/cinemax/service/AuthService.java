// package service;

import java.util.ArrayList;
import java.util.List;
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

	AuthService() { }

	public final class AccessAuth implements CsvProcessor {
		private String usernameMatch, plainPass;
		public boolean processing;

		private AccessAuth(String userMatch, String plainPass) {
			this.usernameMatch = userMatch;
			this.plainPass = plainPass;
			processing = true;
		}

		// file format: username, iterations:hashedPsswd:hashSalt, name, surname, dateOfBirth, domicile, role
		@Override
		public boolean process(String line) {
			String[] sub = line.split(",");

			if (sub.length < 7)
				return false;

			if (this.usernameMatch.equals(sub[0])) {
				PasswordService.compareStringToHash( plainPass, sub[0] );
			}

			return false;
		}
	}

	public final class SignupProcessor implements CsvProcessor {
		public boolean processing;

		SignupProcessor( String username ) {

		}

		@Override
		public boolean process(String line) {
			String[] sub = line.split( "," );
			// TODO
			return false;
		}
		
	}

	private record SignupResult( boolean success, boolean usernameExists, ArrayList<String> passwordConditions ) {}
	public static SignupResult signup( String username, String password ) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		final var NOT_OK = "[ X ]";

		var success = false;
		var usernameExists = false;
		ArrayList<String> passwordConditions = new ArrayList<>(List.of(
			
		));
		var result = new SignupResult(false, false, null);
		
		try
		{
			CsvReader.INSTANCE.setProcessor( AuthService.INSTANCE.new SignupProcessor() );
			CsvReader.INSTANCE.process( FilePaths.USERS.getPath() );
		}
		catch ( ParsingException e ) {
			success = false;
		}

		return result;
	}

	public static boolean login(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		AccessAuth retriever = INSTANCE.new AccessAuth(username, password);

		CsvReader.INSTANCE.setProcessor(retriever);

		try {
			boolean result = CsvReader.INSTANCE.process( FilePaths.USERS.getPath() );
			return result;
		} catch (ParsingException e) {
			return false;
		}
	}


	public User getUser() throws UserNotFoundException {
		// Used to find eventual bugs in the code. User should exist at all times, after assignment in 
		// classpath root main method
		if ( user == null ) throw new UserNotFoundException();

		return user;
	}
	
	public void setUser( User newUser ) {
		user = newUser;
	}

	/**
	 * Equivalent to setUser( new Guest() ).
	 */
	public void logout() {
		user = new Guest();
	}
}
