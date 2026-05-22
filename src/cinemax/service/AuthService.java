package service;

import model.user.Guest;
import model.user.User;

/**
 * Class dedicated to authenticating users and ensuring that maximum 1 authenticated user exists at all times.
 */
public final class AuthService {
	private static User user;

	public AuthService()
	{
		user = new Guest();
	}

	public boolean login()
	{ return false; }

	public boolean logout()
	{ return false; }

	public User getUser()
	{ return this.user; }
}
