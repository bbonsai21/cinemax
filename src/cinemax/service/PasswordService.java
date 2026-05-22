package service;

import java.util.Objects;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * This class is meant to create secure hashes, starting from plain text.
 * It doesn't know the state of other memory object nor does it interact with files.
 * @see AuthService
 */
public class PasswordService {
	private PasswordService() {}
	
	private static String getSalt()
	{
		SecureRandom sr; 
		try {
			sr = SecureRandom.getInstance( "SHA1PRNG" );
		} catch( NoSuchAlgorithmException e ) { throw new RuntimeException( e.getMessage() ); }

		byte[] salt = new byte[16];
		sr.nextBytes( salt );

		return salt.toString();
	}

	/**
	 * Creates and returns a secure hash, given a plain string password and a salt.
	 * @param password plain text string to hash
	 * @param salt salt string
	 * @return String hashed password
	 * @see #getHash(String)
	 * @see #getHashAndSalt(String)
	 */
	public static String getHash( String password, String salt )
	{
		Objects.requireNonNull( password );
		Objects.requireNonNull( salt );

		MessageDigest md;
		try {
			md = MessageDigest.getInstance( "SHA-256" );
			md.update( salt.getBytes() );
			byte[] bytes = md.digest( password.getBytes() );

			StringBuilder sb = new StringBuilder();
			for ( int i = 0; i < bytes.length; i++ )
			{
				sb.append( Integer.toString( ( bytes[i] & 0xff ) + 0x100, 16 ).substring( 1 ) );
			}

			return sb.toString();
		}
		catch( NoSuchAlgorithmException e )
		{ throw new RuntimeException( e.getMessage() ); }
	}

	/**
	 * Creates and returns a secure hash and a salt, given a plain string password.
	 * @param password plain text string to hash
	 * @return String hashed password
	 * @see #getHashAndSalt(String)
	 * @see #getHash(String, String)
	 */
	public static String getHash( String password )
	{
		Objects.requireNonNull( password );

		return getHash( password, getSalt() );
	}

	/**
	 * Creates and returns an array of 2 strings, containing the provided password hashed, and the salt used to hash it.
	 * @param password plain text string to hash
	 * @return String[] array of 2 strings
	 * @see #getHash(String)
	 * @see #getHash(String, String)
	 */
	public static String[] getHashAndSalt( String password )
	{
		Objects.requireNonNull( password );

		String[] res = new String[2];

		String salt = getSalt();

		res[0] = getHash( password, salt );
		res[1] = salt;

		return res;
	}

	/**
	 * Checks if a plain text password coresponds to a hashed password, given the salt
	 * @param password plain text password
	 * @param hashedPassword hashed password to check password against
	 * @param salt salt of the hashed password
	 * @return
	 */
	public static boolean checkPasswordCorespondence( String password, String hashedPassword, String salt )
	{
		return ( getHash( password, salt ) == hashedPassword );
	}
}
