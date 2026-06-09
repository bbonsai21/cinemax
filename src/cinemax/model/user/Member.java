package model.user;

/**
 * Defines the class for members.
 * 
 * @see AuthenticatedUser
 * @see User
 */
public final class Member extends AuthenticatedUser {
    public Member(String username, String name, String surname, String domicile) {
        super(username, name, surname, domicile);
    }
}
