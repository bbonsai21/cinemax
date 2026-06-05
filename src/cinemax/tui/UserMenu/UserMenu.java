package tui.UserMenu;

import java.util.ArrayList;
import java.util.List;

import service.AuthService;
import tui.Displayer;
import tui.Input;
import tui.LogoutException;
import tui.MenuEntry;
import tui.Message;

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
     * 
     * @return list of entries
     */
    protected abstract ArrayList<MenuEntry> buildEntries();

    public abstract void run();

    private void changeLanguage() {
        var languages = new ArrayList<>(List.of(
                new MenuEntry(Message.get("menu.return"), null),
                new MenuEntry("EN", null),
                new MenuEntry("IT", null)));

        MenuEntry choice = Input.INSTANCE.choose(languages);

        if (choice.labelKey() == "menu.return")
            return;

        Message.setLanguage(choice.labelKey().toLowerCase());
    }

    protected ArrayList<MenuEntry> getSharedEntries() {
        return new ArrayList<>(List.of(
                new MenuEntry("language.change", this::changeLanguage)));
    }

    protected ArrayList<MenuEntry> getSharedLoggedEntries() {
        return new ArrayList<>(List.of(
                new MenuEntry("menu.logout", this::logout)));
    }

    private void logout() throws LogoutException {
        var authservice = AuthService.init();
        authservice.logout();

        throw new LogoutException();
    }
}
