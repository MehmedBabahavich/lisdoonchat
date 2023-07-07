package net.yarik.todolist.exceptions;

public class UserIsBannedException extends Exception {
    public UserIsBannedException() {
        super();
    }

    public UserIsBannedException(String message) {
        super(message);
    }
}
