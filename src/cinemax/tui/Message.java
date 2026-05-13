package tui;

import java.util.Locale;
import java.util.ResourceBundle;

public final class Message {
        private static String language = "en";
        private static ResourceBundle bundle = ResourceBundle.getBundle( "i18n/messages", Locale.ENGLISH );

        private Message() {}

        public static void setLanguage( String lang ) {
                language = lang;
                bundle = ResourceBundle.getBundle( language );
        }

        public String get( String msgKey, Object... params ) {
                String msg = bundle.getString( msgKey );
                return params.length == 0 ? msg : String.format( msg, params );
        }
}
