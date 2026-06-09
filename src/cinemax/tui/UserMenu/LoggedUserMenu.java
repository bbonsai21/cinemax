package tui.UserMenu;

import java.util.ArrayList;
import java.util.List;

import exception.LogoutException;
import model.user.User;
import service.AuthService;
import tui.MenuEntry;
import tui.Message;

public sealed abstract class LoggedUserMenu extends UserMenu permits MemberMenu, ProjectionistMenu, TicketsClerkMenu {
    User user;

    protected LoggedUserMenu(User user) {
        super();
        this.user = user;
    }

    protected abstract ArrayList<MenuEntry> buildEntries();

    protected ArrayList<MenuEntry> getSharedLoggedEntries() {
        return new ArrayList<>(List.of(
                new MenuEntry(Message.get("menu.logged.logout"), this::logout)));
    }

    private void logout() {
        AuthService.INSTANCE.logout();
        throw new LogoutException();
    }
}
