package model.user;

import java.util.Objects;

/**
 * Class provides ulterior essential fields and methods every authenticated user
 * must have, distinguishing them from Guest
 */
public sealed abstract class AuthenticatedUser extends User permits Member, TicketsClerk, Projectionist {
	private String username, name, surname, domicile;

	protected AuthenticatedUser(String username, String name, String surname, String domicile) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(name);
		Objects.requireNonNull(surname);

		this.username = username;
		this.name = name;
		this.surname = surname;
		this.domicile = domicile;
	}

	public String getUsername() {
		return this.username;
	}

	public String getName() {
		return this.name;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getDomicile() {
		return this.domicile;
	}

	@Override
	public String toString() {
		return this.username;
	}
}
