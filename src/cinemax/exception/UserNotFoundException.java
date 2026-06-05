package exception;

/**
 * Error for when a user isn't found when it should be, instead, present.
 */
public class UserNotFoundException extends NullPointerException {
    public UserNotFoundException() {
        super( "Required user is missing." );
    }
}
