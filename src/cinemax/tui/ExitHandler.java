package tui;

/**
 * Handles application exit explicitly and gracefully.
 */
public class ExitHandler implements Runnable {
    private ExitHandler() { }

    /**
     * Prompts the user to exit. If positive, closes application.
     */
    public static void promptAndExit() {
        String input = Input.readLine( Message.get( "exit.prompt" ) );
        if ( input.toLowerCase().strip().equals( Message.get( "exit.confirm.yes" ).toLowerCase().strip() ) ) {
            IO.println( "Au revoir!" );
            System.exit( 0 );
        }
    }

    @Override
    public void run() {
        promptAndExit();
    }
}
