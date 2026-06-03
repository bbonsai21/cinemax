package service;

import java.util.Objects;

import exception.ParsingException;
import model.user.Guest;
import model.user.User;
import repository.CsvProcessor;
import repository.CsvReader;
import repository.FilePaths;

/**
 * Class dedicated to authenticating users and ensuring that maximum 1 authenticated user exists at all times.
 */
public final class AuthService {
	private static User user;
	private static AuthService self;

	private AuthService()
	{
		user = new Guest();
	}

	public static AuthService init()
	{
		if ( self != null ) return self;

		self = new AuthService();
		return self;
	}

	public class AccessAuth implements CsvProcessor {
		private String usernameMatch, plainPass;
		public boolean processing;
		
		private AccessAuth( String userMatch, String plainPass ) {
			this.usernameMatch = userMatch;
			this.plainPass = plainPass;
			processing = true;
		}

		// file format: username, hashedPsswd, psswdSalt
		@Override
		public boolean process( String line ) {
			String[] sub = line.split( "," );
			
			if ( sub.length < 3 ) return false;

			if ( this.usernameMatch.equals( sub[0] ) ) {
				String hashedPass = PasswordService.getHash( this.plainPass, sub[2] );
				
				if ( hashedPass.equals( sub[1] ) ) return true;
			
			return false;
		}
	}

	public static boolean login( String username, String password )
	{
		Objects.requireNonNull( username );
		Objects.requireNonNull( password );
		
		CsvReader csvReader = CsvReader.init();
		csvReader.setPath( FilePaths.USERS.getPath() );

		AccessAuth retriever = self.new AccessAuth( username, password  );

		csvReader.setProcessor( retriever);		

		try {
			boolean result = csvReader.process();
			return result;
		} catch( ParsingException e ) {
			return false;
		}
	}

	public void logout()
	{ user = new Guest(); }

	public User getUser()
	{ return user; }
}
