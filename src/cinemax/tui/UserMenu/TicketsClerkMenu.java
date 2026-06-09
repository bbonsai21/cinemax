package tui.UserMenu;

import java.util.ArrayList;

import model.user.User;
import tui.MenuEntry;

public final class TicketsClerkMenu extends LoggedUserMenu {
    public TicketsClerkMenu(User user) {
        super(user);
    }

    protected ArrayList<MenuEntry> buildEntries() {
        ArrayList<MenuEntry> entries = new ArrayList<>();

        return entries;
    }

    public void run() {

    }
}
