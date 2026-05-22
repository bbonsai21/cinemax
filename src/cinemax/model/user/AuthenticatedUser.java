package model.user;

/**
 * Class provides ulterior essential fields and methods every authenticated user must have, distinguishing them from Guest
 */
public sealed abstract class AuthenticatedUser extends User permits Member, TicketsClerk, Projectionist {
	private String username;
	private String hashedPass;

	public String getUsername()
	{ return this.username; }

	public String getHashedPass()
	{ return this.hashedPass; }

	@Override
	public String toString()
	{ return this.username; }
}
