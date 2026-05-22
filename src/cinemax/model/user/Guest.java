package model.user;

/**
 * Defines the class for guests, un-authenticated users.
 * @see AuthenticatedUser
 * @see User
 */
public final class Guest extends User {
	public Guest()
	{}

	@Override
	public String toString()
	{ return "Guest"; }
}
