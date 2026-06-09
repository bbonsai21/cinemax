import tui.Input;

/**
 * Class meant for final clean-up upon program exit.
 */
public enum Cleanup {
    INSTANCE;

    Cleanup() { }
    
    public void run()
    {
        Input.close();
    }
}
