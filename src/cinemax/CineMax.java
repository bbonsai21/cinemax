import java.util.ArrayList;

import model.user.Guest;
import service.AuthService;
import tui.Displayer;
import tui.Input;
import tui.MenuDispatcher;
import tui.MenuEntry;
import tui.Message;

void main(String[] args) {
        Displayer.cleanScreen();

        Runtime.getRuntime().addShutdownHook(
                        new Thread(() -> {
                                try {
                                        Cleanup.INSTANCE.run();
                                } catch (Exception e) {
                                        IO.println("Cleanup failed (" + e.getMessage() + "). Printing stack trace.");
                                        e.printStackTrace();
                                }
                        }));

        // Start
        Displayer.section("Select your language");
        var languageMenu = new ArrayList<MenuEntry>(List.of(
                        new MenuEntry("EN", null),
                        new MenuEntry("IT", null)));
        MenuEntry languageChoiceEntry = Input.choose(languageMenu);

        Message.setLanguage(languageChoiceEntry.labelKey().toLowerCase());

        Displayer.cleanScreen();

        Displayer.title("Cinemax", Message.get("welcome.subtitle"), "right");

        Displayer.body(Message.get("message.await.enter"));

        Input.awaitInput();

        AuthService.INSTANCE.setUser( new Guest() );
        MenuDispatcher.INSTANCE.dispatch( AuthService.INSTANCE.getUser() );
}