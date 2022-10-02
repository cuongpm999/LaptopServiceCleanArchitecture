package vn.ptit.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String s) {
        super(s);
    }
    public InvalidRequestException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
