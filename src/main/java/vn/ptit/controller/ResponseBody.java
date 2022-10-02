package vn.ptit.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBody {
    private Object result;
    private int status;
    private String message;
    private int code;

    public ResponseBody(Object result, Status status, String message, Code code) {
        this.result = result;
        this.status = status.value();
        this.message = message;
        this.code = code.value();
    }

    public ResponseBody(Object result, Status status, String message, int code) {
        this.result = result;
        this.status = status.value();
        this.message = message;
        this.code = code;
    }

    public ResponseBody(Object result, Status status, Code code) {
        this.result = result;
        this.status = status.value();
        this.message = code.message();
        this.code = code.value();
    }

    public enum Status {
        SUCCESS(1),
        FAILED(0);

        private final int value;

        Status(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    public enum Code {
        SUCCESS(200, "Successful"),
        UNAUTHORIZED_REQUEST(403, "Unauthorized request"),
        NOT_FOUND(404, "Not found"),
        BAD_REQUEST(400, "Bad request"),
        INTERNAL_ERROR(500, "Internal server error");

        private final int value;
        private final String message;

        Code(int value, String message) {
            this.value = value;
            this.message = message;
        }

        public String message() {
            return this.message;
        }

        public int value() {
            return this.value;
        }
    }
}
