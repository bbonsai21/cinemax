import tui.Displayer;
import tui.UserMenu.GuestMenu;

void main( String[] args )
{
        Displayer.title( "Cinemax", "A Practical Cinema Manager", "right" );

        GuestMenu guestMenu = new GuestMenu();
        guestMenu.show();
}