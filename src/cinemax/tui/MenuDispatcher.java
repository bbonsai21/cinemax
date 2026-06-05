package tui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.user.*;
import tui.UserMenu.GuestMenu;
import tui.UserMenu.MemberMenu;
import tui.UserMenu.ProjectionistMenu;
import tui.UserMenu.TicketsClerkMenu;
import tui.UserMenu.UserMenu;

/**
 * Dispatching class for user menus, providing to each one of them the necessary ojbects.
 */
public enum MenuDispatcher {
	INSTANCE;

	MenuDispatcher() {
	}

	/**
	 * Dispatches the right menu for the provided user.
	 * 
	 * @param user the user
	 * @return user menu for the coresponding user
	 */
	public void dispatch(User user) {
		Objects.requireNonNull(user);

		switch (user) {
			case Guest g -> new GuestMenu().run();
			case Member m -> new MemberMenu().run();
			case TicketsClerk t -> new TicketsClerkMenu().run();
			case Projectionist p -> new ProjectionistMenu().run();
		};
	}
}
