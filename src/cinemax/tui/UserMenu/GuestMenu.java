package tui.UserMenu;

import java.util.ArrayList;
import java.util.List;

import exception.LogoutException;
import exception.ValidationException;
import model.user.User;
import service.AuthService;
import service.PasswordService;
import service.AuthService.LoginResult;
import service.SignupValidation;
import tui.Displayer;
import tui.Input;
import tui.MenuEntry;
import tui.Message;

public class GuestMenu extends UserMenu {
    public GuestMenu() {
        super();
    }

    protected ArrayList<MenuEntry> buildEntries() {
        ArrayList<MenuEntry> options = getSharedEntries();
        options.addAll(new ArrayList<>(List.of(
                new MenuEntry(Message.get("menu.guest.signup"), this::signup),
                new MenuEntry(Message.get("menu.guest.login"), this::login))));

        return options;
    }

    public void run() {
        while (true) {
            try {
                Displayer.cleanScreen();

                Displayer.title("GUEST");

                MenuEntry choice = Input.choose(this.entries); // entries inherited from UserMenu
                choice.action().run();
            } catch (LogoutException e) {
                continue;
            }
        }
    }

    private void signup() {
        Displayer.cleanScreen();

        Displayer.title(Message.get("menu.guest.signup"));

        String username = Input.readLine(Message.get("menu.guest.login.prompt.username"));
        char[] password = Input.readSecureLine(Message.get("menu.guest.login.prompt.password"));
        char[] password_confirm = Input.readSecureLine(Message.get("menu.guest.login.prompt.password.confirm"));

        if (!java.util.Arrays.equals(password, password_confirm)) {
            Displayer.body(Message.get("menu.guest.login.password.notMatching"));

            java.util.Arrays.fill(password, '\0');
            java.util.Arrays.fill(password_confirm, '\0');

            Input.awaitInput();
            return;
        }

        java.util.Arrays.fill(password_confirm, '\0');
        SignupValidation validation;
        try {
            validation = AuthService.validateSignup(username, password);
        } catch (ValidationException e) {
            Displayer.body(Message.get(e.getMessage()));
            Input.awaitInput();
            return;
        }

        String passHash = PasswordService.getHash(password);
        java.util.Arrays.fill(password, '\0');

        if (!validation.isValid()) {
            Displayer.body(Message.get("menu.guest.signup.username.unavailable"));
            if (!validation.usernameAvailable())
                Displayer.body(Message.get("menu.guest.signup.username.taken"));
            if (!validation.usernameValid())
                Displayer.body(Message.get("menu.guest.signup.username.invalid"));
            if (!validation.passwordLongEnough())
                Displayer.body(Message.get("menu.guest.signup.password.length", 7));
            if (!validation.passwordHasUppercase())
                Displayer.body(Message.get("menu.guest.signup.password.uppercase"));
            if (!validation.passwordHasDigit())
                Displayer.body(Message.get("menu.guest.signup.password.digit"));
            if (!validation.passwordHasSpecial())
                Displayer.body(
                        Message.get("menu.guest.signup.password.special", AuthService.getAllowedSpecialCharacter()));
            if (!validation.passwordOnlyAllowedSpecial())
                Displayer.body(Message.get("menu.guest.signup.password.specialInvalid",
                        AuthService.getAllowedSpecialCharacter()));

            Input.awaitInput();
            return;
        }

        String name = Input.readLine(Message.get("menu.guest.signup.name.prompt"));
        if (name.strip().equals("")) {
            java.util.Arrays.fill(password, '\0');
            Displayer.body(Message.get("menu.guest.signup.name.empty"));
            Input.awaitInput();
            return;
        }
        if (name.contains(",")) {
            Displayer.body(Message.get("menu.guest.signup.illegalCharacter"));
            Input.awaitInput();
            return;
        }

        String surname = Input.readLine(Message.get("menu.guest.signup.surname.prompt"));
        if (surname.strip().equals("")) {
            java.util.Arrays.fill(password, '\0');
            Displayer.body(Message.get("menu.guest.signup.surname.empty"));
            Input.awaitInput();
            return;
        }
        if (surname.contains(",")) {
            Displayer.body(Message.get("menu.guest.signup.illegalCharacter"));
            Input.awaitInput();
            return;
        }

        String dateOfBirth = Input.readLine(Message.get("menu.guest.signup.dateOfBirth.prompt"));
        dateOfBirth.strip();
        if (dateOfBirth.contains(",")) {
            Displayer.body(Message.get("menu.guest.signup.illegalCharacter"));
            Input.awaitInput();
            return;
        }

        String domicile = Input.readLine(Message.get("menu.guest.signup.domicile.prompt"));
        if (domicile.strip().equals("")) {
            java.util.Arrays.fill(password, '\0');
            Displayer.body(Message.get("menu.guest.signup.domicile.empty"));
            Input.awaitInput();
            return;
        } else if (domicile.contains(",")) {
            Displayer.body(Message.get("menu.guest.signup.illegalCharacter"));
            Input.awaitInput();
            return;
        }

        boolean signupResult = AuthService.signup(username, passHash, name, surname, dateOfBirth, domicile);
        if (signupResult) {
            Displayer.body(Message.get("menu.guest.signup.successful"));
            Input.awaitInput();
            return;
        }
        Displayer.body(Message.get("menu.guest.signup.failed"));
        Input.awaitInput();
    }

    private void login() {
        Displayer.cleanScreen();

        Displayer.title(Message.get("menu.guest.login"));

        String username = Input.readLine(Message.get("menu.guest.login.prompt.username"));
        char[] password = Input.readSecureLine(Message.get("menu.guest.login.prompt.password"));

        LoginResult loginResult = AuthService.login(username, password);

        java.util.Arrays.fill(password, '\0');

        String name, surname;
        User user;

        // of course on a real project we'd simply be making a request to a server that
        // stores all the logins
        // and act from there; this is a simulation, we don't care about the fact that
        // irl this would make no sense lol; we store everything in the same filesystem,
        // here.
        if (loginResult.successful()) {
            user = loginResult.user();
            name = loginResult.name();
            surname = loginResult.surname();
        } else {
            Displayer.body(Message.get("menu.guest.login.failed"));
            Input.awaitInput();
            return;
        }
    }
}
