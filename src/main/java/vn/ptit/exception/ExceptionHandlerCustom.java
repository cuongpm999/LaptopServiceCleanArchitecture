package vn.ptit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.ptit.controller.ResponseBody;
import vn.ptit.model.PagingPayload;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerCustom {

    @ExceptionHandler({Exception.class, InternalServerException.class})
    public ResponseEntity<Object> handleInternalException(Exception e) {
        return handleInternalServerException(e);
    }

    @ExceptionHandler({UnauthorizedException.class, InvalidRequestException.class, DataNotFoundException.class})
    public ResponseEntity<Object> handleCustomException(Exception e) {
        e.printStackTrace();
        if (e instanceof UnauthorizedException) {
            return handleUnauthorizedException((UnauthorizedException) e);
        } else if (e instanceof InvalidRequestException) {
            return handleInvalidRequestException((InvalidRequestException) e);
        } else if (e instanceof DataNotFoundException) {
            return handleDataNotFoundException((DataNotFoundException) e);
        } else {
            return handleInternalServerException(e);
        }
    }

    @ExceptionHandler({InvalidDataException.class})
    public ResponseEntity<Object> handleCustomDataException(Exception e) {
        e.printStackTrace();
        log.error("Invalid data format: " + e.getMessage(), e);
        return ResponseEntity.ok(
                new ResponseBody(PagingPayload.empty(),
                        ResponseBody.Status.FAILED,
                        e.getMessage(),
                        ResponseBody.Code.BAD_REQUEST)
        );
    }

    protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        ex.printStackTrace();
        log.error("Unauthorized request: " + ex.getMessage(), ex);
        return ResponseEntity.ok(
                new ResponseBody(PagingPayload.empty(),
                        ResponseBody.Status.FAILED,
                        ex.getMessage(),
                        ResponseBody.Code.UNAUTHORIZED_REQUEST)
        );
    }

    protected ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException e) {
        e.printStackTrace();
        log.error("Invalid request format: " + e.getMessage(), e);
        return ResponseEntity.ok(
                new ResponseBody(PagingPayload.empty(),
                        ResponseBody.Status.FAILED,
                        e.getMessage(),
                        ResponseBody.Code.BAD_REQUEST)
        );
    }

    protected ResponseEntity<Object> handleInternalServerException(Exception e) {
        e.printStackTrace();
        log.error("Internal server error: " + e.getMessage(), e);
        return ResponseEntity.ok(
                new ResponseBody(PagingPayload.empty(),
                        ResponseBody.Status.FAILED,
                        e.getMessage(),
                        ResponseBody.Code.INTERNAL_ERROR)
        );
    }

    protected ResponseEntity<Object> handleDataNotFoundException(Exception e) {
        e.printStackTrace();
        log.error("Data not found error: " + e.getMessage(), e);
        return ResponseEntity.ok(
                new ResponseBody(PagingPayload.empty(),
                        ResponseBody.Status.FAILED,
                        e.getMessage(),
                        ResponseBody.Code.NOT_FOUND)
        );
    }
}
