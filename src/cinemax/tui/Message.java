package tui;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * i18n string retriever class.
 */
public final class Message {
        private static String language = "en";
        private static ResourceBundle bundle = ResourceBundle.getBundle( "i18n/messages", Locale.ENGLISH );

        private Message() {}

        /**
         * Sets the language which the class refers to for retrieving translations.
         * @param lang identifier of the language (e.g. 'en')
         */
        public static void setLanguage( String lang ) {
                Objects.requireNonNull( lang );

                language = lang;
                bundle = ResourceBundle.getBundle( language );
        }

        /**
         * Returns a string from the coresponding i18n file.
         * @param msgKey key to the message
         * @param params eventually-null variable amount of parameters
         * @return String the string in the chosen language
         * @see #setLanguage(String)
         */
        public static String get( String msgKey, Object... params ) {
                Objects.requireNonNull( msgKey );

                String msg = bundle.getString( msgKey );
                return params.length == 0 ? msg : String.format( msg, params );
        }
}
