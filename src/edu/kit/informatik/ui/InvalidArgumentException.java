package edu.kit.informatik.ui;

public class InvalidArgumentException extends Exception{
    private ErrorMessage errorMessage;

    public InvalidArgumentException(final ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage.getMessage();
    }
}
