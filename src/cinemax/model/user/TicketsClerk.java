package model.user;

/**
 * Defines the class for tickets clerks.
 * 
 * @see AuthenticatedUser
 * @see User
 */
public final class TicketsClerk extends AuthenticatedUser {
    public TicketsClerk(String username, String name, String surname, String domicile) {
        super(username, name, surname, domicile);
    }
}
