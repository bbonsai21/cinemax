package tui.UserMenu;

import model.user.User;

public sealed abstract class LoggedUserMenu permits MemberMenu, ProjectionistMenu, TicketsClerkMenu {
    User user;

    protected LoggedUserMenu(User user) {
        this.user = user;
    }
}
