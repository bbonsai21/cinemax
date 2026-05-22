package exception;

import model.ParsingError;

public final class ParsingException extends Exception {
        private final ParsingError error;

        public ParsingException( ParsingError error ) {
                super( error.getMsgKey() );

                this.error = error;
        }

        public ParsingError getError()
        { return this.error; }
}
