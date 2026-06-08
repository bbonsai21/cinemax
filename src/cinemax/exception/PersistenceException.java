package exception;

import model.PersistenceError;

public final class PersistenceException extends Exception {
        private final PersistenceError error;

        public PersistenceException(PersistenceError error) {
                super(error.getMsgKey());

                this.error = error;
        }

        public PersistenceError getError() {
                return this.error;
        }
}