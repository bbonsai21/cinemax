package model.user;

/**
 * Defines the class for a projectionist.
 * 
 * @see AuthenticatedUser
 * @see User
 */
public final class Projectionist extends AuthenticatedUser {
    public Projectionist(String username, String name, String surname, String domicile) {
        super(username, name, surname, domicile);
    }
}
