package tui;

import tui.UserMenu.GuestMenu;

public class LoginMenu {
    public LoginMenu() {}

    public static void show() {
        Displayer.title( "Cinemax", "A Practical Cinema Manager", "right" );

        GuestMenu guestMenu = new GuestMenu();
        guestMenu.show();
    }
}
