package tui;

import java.util.Objects;

import model.user.*;
import tui.UserMenu.GuestMenu;
import tui.UserMenu.MemberMenu;
import tui.UserMenu.ProjectionistMenu;
import tui.UserMenu.TicketsClerkMenu;

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
			case Guest _ -> new GuestMenu().run();
			case Member _ -> new MemberMenu(user).run();
			case TicketsClerk _ -> new TicketsClerkMenu(user).run();
			case Projectionist _ -> new ProjectionistMenu(user).run();
		};
	}
}
