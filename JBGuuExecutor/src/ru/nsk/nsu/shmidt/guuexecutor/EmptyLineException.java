package ru.nsk.nsu.shmidt.guuexecutor;

public class EmptyLineException extends Throwable {
    private String message;

    public EmptyLineException(String message) {
        super(message);
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
