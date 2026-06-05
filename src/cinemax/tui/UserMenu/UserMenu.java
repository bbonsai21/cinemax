package tui.UserMenu;

import java.util.ArrayList;

import tui.MenuEntry;

/**
 * Abstract representation of a user menu.
 */
public abstract class UserMenu implements Runnable {
    protected final ArrayList<MenuEntry> entries;
    
    protected UserMenu() {
        entries = buildEntries();
    }

    /**
     * Builds the entries the class can make use of
     * @return ArrayList<MenuEntry> list of entries
     */
    protected abstract ArrayList<MenuEntry> buildEntries();

    public abstract void run();
}
