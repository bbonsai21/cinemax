package service;

import java.util.Base64;
import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * This class is meant to create secure hashes, starting from plain text.
 * It doesn't know the state of other memory object nor does it interact with files.
 * @see AuthService
 */
public class PasswordService {
	private PasswordService() {}
	
	private static String getSalt()
	{
		SecureRandom sr = new SecureRandom();
		byte[] bytes = new byte[16];
		sr.nextBytes( bytes );

		return Base64.getEncoder().encodeToString( bytes );
	}

	/**
	 * Returns the hashed password, containing also the salt, in itself.
	 * @param password plain text to hash
	 * @param salt
	 * @return String hashed password
	 * @see #getHash(String)
	 */
	public static String getHash( String password, String salt )
	{
		Objects.requireNonNull( password );
		Objects.requireNonNull( salt );

		int iterations = 310000; // 310k

		PBEKeySpec spec = new PBEKeySpec( password.toCharArray(), salt.getBytes( StandardCharsets.UTF_8 ), iterations, 8*64 );
		SecretKeyFactory skf;
		try { skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA256" ); }
		catch( NoSuchAlgorithmException e ) { throw new RuntimeException( e.getMessage() ); }

		byte[] hash;
		try { hash = skf.generateSecret( spec ).getEncoded(); }
		catch( InvalidKeySpecException e ) { throw new RuntimeException( e.getMessage() ); }

		return Integer.toString( iterations ) + ":" + Base64.getEncoder().encodeToString( hash ) + ":" + salt;
	}

	/**
	 * Returns the hashed password.
	 * @param password plain text to hash
	 * @return String
	 * @see #getHash(String, String)
	 */
	public static String getHash( String password )
	{
		return getHash( password, getSalt() );
	}

	public static String getHash( char[] password, String salt ) {
		Objects.requireNonNull( password );
		Objects.requireNonNull( salt );

		int iterations = 310000; // 310k

		PBEKeySpec spec = new PBEKeySpec( password, salt.getBytes( StandardCharsets.UTF_8 ), iterations, 8*64 );
		SecretKeyFactory skf;
		try { skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA256" ); }
		catch( NoSuchAlgorithmException e ) { throw new RuntimeException( e.getMessage() ); }

		byte[] hash;
		try { hash = skf.generateSecret( spec ).getEncoded(); }
		catch( InvalidKeySpecException e ) { throw new RuntimeException( e.getMessage() ); }

		return Integer.toString( iterations ) + ":" + Base64.getEncoder().encodeToString( hash ) + ":" + salt;
	}

	public static String getHash( char[] password ) {
		return getHash( password, getSalt() );
	}

	/**
	 * Returns whether the provided plain text string matches the hash
	 * @param plainText password in plain text
	 * @param hash the hash against which to compare
	 * @return boolean
	 */
	public static boolean compareStringToHash( String plainText, String hash )
	{
		String salt = hash.split( ":" )[2];
		return MessageDigest.isEqual( getHash( plainText, salt ).getBytes( StandardCharsets.UTF_8 ), hash.getBytes( StandardCharsets.UTF_8 ) );
	}

	/**
	 * Returns whether the provided plain text char array matches the hash
	 * @param plainText char array containing the plain password
	 * @param hash the hash against which to compare
	 * @return boolean
	 */
	public static boolean compareCharArrToHash( char[] plainText, String hash ) {
		String salt = hash.split( ":" )[2];
		return MessageDigest.isEqual( getHash(plainText, salt).getBytes( StandardCharsets.UTF_8 ), hash.getBytes( StandardCharsets.UTF_8 ) );
	}
}