package model;

import java.util.Objects;

/**
 * Enum class meant to store errors that can be encountered during .csv parsing.
 * @see ParsingException
 * @see ValidationError
 */
public enum ParsingError {
        PARSING_NO_SUCH_ENTRY( "parsing.noSuchEntry" ),
        PARSING_COULD_NOT_ADD( "parsing.couldNotAdd" );

        private final String msgKey;
        private final Object[] params;

        ParsingError( String msgKey, Object... params ) {
                Objects.requireNonNull( msgKey );

                this.msgKey = msgKey;
                this.params = params;
        }
        
        public String getMessageKey()
        { return this.msgKey; }

        public Object[] getParams() 
        { return this.params; }
}
