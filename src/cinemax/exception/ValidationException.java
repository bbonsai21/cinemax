package exception;

import model.ValidationError;

public final class ValidationException extends Exception {
        private final ValidationError error;

        public ValidationException(ValidationError error) {
                super( ValidationError.getMessageKey( error ) );

                this.error = error;
        }

        public ValidationError getError()
        { return this.error; }
}
