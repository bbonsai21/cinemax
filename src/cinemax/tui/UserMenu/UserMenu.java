package tui.UserMenu;

import java.util.ArrayList;
import java.util.List;

import exception.LogoutException;
import service.AuthService;
import tui.Displayer;
import tui.ExitHandler;
import tui.Input;
import tui.MenuEntry;
import tui.Message;

/**
 * Abstract representation of a user menu.
 */
public abstract class UserMenu implements Runnable {
    protected ArrayList<MenuEntry> entries;

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

    public void rebuildEntries() {
        this.entries = buildEntries();
    }

    private void changeLanguage() {
        Displayer.cleanScreen();

        Displayer.title(Message.get("menu.language.change"));

        var languages = new ArrayList<>(List.of(
                new MenuEntry(Message.get("menu.return"), null),
                new MenuEntry("EN", null),
                new MenuEntry("IT", null)));

        MenuEntry choice = Input.choose(languages);

        if (choice.labelKey().equals("menu.return"))
            return;

        Message.setLanguage(choice.labelKey().toLowerCase());

        rebuildEntries();
    }

    private void displayExtraInfo() {
        Displayer.cleanScreen();

        Displayer.title("Cinemax");
        Displayer.section(Message.get("menu.extra.section.credits"));
        Displayer.body(Message.get("menu.extra.developer", "https://github.com/bbonsai21"));

        Displayer.body(Message.get("message.await.enter"));
        Input.awaitInput();

        Displayer.cleanScreen();

        Displayer.title("Cinemax");
        Displayer.section(Message.get("menu.extra.section.license"));
        Displayer.fromFile("LICENSE.txt",
                "There was an error displaying the license.\nYou can still check it in the project's root folder file LICENSE.txt");

        Displayer.body(Message.get("message.await.enter"));
        Input.awaitInput();
    }

    protected ArrayList<MenuEntry> getSharedEntries() {
        return new ArrayList<>(List.of(
                new MenuEntry(Message.get("menu.language.change"), this::changeLanguage),
                new MenuEntry(Message.get("menu.extra"), this::displayExtraInfo),
                new MenuEntry(Message.get("menu.quit"), ExitHandler::promptAndExit)));
    }

    protected ArrayList<MenuEntry> getSharedLoggedEntries() {
        return new ArrayList<>(List.of(
                new MenuEntry("menu.logout", this::logout)));
    }

    private void logout() throws LogoutException {
        AuthService.INSTANCE.logout();
        throw new LogoutException();
    }
}
