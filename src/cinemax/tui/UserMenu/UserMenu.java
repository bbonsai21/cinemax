package tui.UserMenu;

import java.util.ArrayList;

import tui.MenuEntry;

public abstract class UserMenu {
    protected ArrayList<MenuEntry> entries;
    
    protected UserMenu() {
        entries = buildEntries();
    }

    protected abstract ArrayList<MenuEntry> buildEntries();
}
