package tui.UserMenu;

import java.util.ArrayList;
import java.util.List;

import model.user.User;
import tui.Displayer;
import tui.Input;
import tui.MenuEntry;

public final class MemberMenu extends LoggedUserMenu {
    public MemberMenu(User user) {
        super(user);
    }

    protected ArrayList<MenuEntry> buildEntries() {
        ArrayList<MenuEntry> entries = getSharedEntries();
        entries.addAll(getSharedLoggedEntries());
        entries.addAll(new ArrayList<>(List.of(
            
        )));

        return entries;
    }

    public void run() {
        while (true) {
            Displayer.cleanScreen();

            Displayer.title("MEMBER");

            MenuEntry choice = Input.choose(this.entries);
            choice.action().run();
        }
    }
}
