package model;

public enum PersistenceError {
    WRITE_FAILED("error.persistence.write.failed"),
    READ_FAILED("error.persistence.read.failed");

    private final String messageKey;

    PersistenceError(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMsgKey() {
        return messageKey;
    }
}