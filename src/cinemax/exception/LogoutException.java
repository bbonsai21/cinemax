package exception;

/**
 * Exception thrown to easily manage the user menu flow, and log the user out.
 */
public class LogoutException extends RuntimeException {
    public LogoutException() {
        super( null, null, true, false );
    }
}
