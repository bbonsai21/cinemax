package tui;

/**
 * Exception thrown to easily manage the user menu flow, and log the user out.
 */
public class LogoutException extends Exception {
    public LogoutException( String key ) {
        super( null, null, true, false );
    }
}
