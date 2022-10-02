package vn.ptit.exception;

public class InternalServerException extends Exception {
    public InternalServerException(String s) {
        super(s);
    }

    public InternalServerException(String s, Throwable throwable) {
        super(s, throwable);
    }
}

