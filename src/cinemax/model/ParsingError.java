package model;

import java.util.Objects;

import exception.ParsingException;

/**
 * Enum class meant to store errors that can be encountered during .csv parsing.
 * @see ParsingException
 * @see ValidationError
 */
public enum ParsingError {
	CSVPARSER_FILE_INVALID( "csvparser.file.invalid" ),
	CSVPARSER_FILE_EOF( "csvparser.file.eof" ),
	CSVPARSER_FILE_CHARCODING_INVALID( "csvparser.file.charcoding.invalid" ),
	CSVPARSER_FILE_CHARCONVERSION_INVALID( "csvparser.file.charconversion.invalid" ),
	// generic error
	CSVPARSER_IOEXCEPTION( "csvparser.file.ioexception" ),

        PARSING_NO_SUCH_ENTRY( "parsing.noSuchEntry" ),
        PARSING_COULD_NOT_ADD( "parsing.couldNotAdd" );

        private final String msgKey;
        private final Object[] params;

        ParsingError( String msgKey, Object... params ) {
                Objects.requireNonNull( msgKey );

                this.msgKey = msgKey;
                this.params = params;
        }
        
        public String getMsgKey()
        { return this.msgKey; }

        public Object[] getParams() 
        { return this.params; }
}
