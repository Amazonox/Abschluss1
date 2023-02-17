package edu.kit.informatik.ui;

public class InvalidArgumentException extends Exception {
    private final ErrorMessage errorMessage;

    public InvalidArgumentException(final ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return this.errorMessage.getMessage();
    }
}
