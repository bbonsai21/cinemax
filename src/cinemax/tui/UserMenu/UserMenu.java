package tui.UserMenu;

import java.util.ArrayList;

import tui.MenuEntry;

public abstract class UserMenu implements Runnable {
    protected final ArrayList<MenuEntry> entries;
    
    protected UserMenu() {
        entries = buildEntries();
    }

    protected abstract ArrayList<MenuEntry> buildEntries();

    public abstract void run();
}
