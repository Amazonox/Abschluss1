package edu.kit.informatik.ui;

public class GameException extends Exception {
    private final ErrorMessage errorMessage;

    public GameException(final ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return this.errorMessage.getMessage();
    }
}
