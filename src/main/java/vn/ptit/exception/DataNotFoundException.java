package vn.ptit.exception;

public class DataNotFoundException extends Exception{
    public DataNotFoundException(String s) {
        super(s);
    }
    public DataNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
