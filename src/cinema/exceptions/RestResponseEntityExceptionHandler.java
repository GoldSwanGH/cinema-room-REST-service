package cinema.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

class ErrorMessage {
    String error;

    public ErrorMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            TicketNotFoundException.class, WrongRowColumnNumberException.class,
            WrongTokenException.class, WrongPasswordException.class} )
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String message = "An unknown exception occurred!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex.getClass() == TicketNotFoundException.class) {
            message = "The ticket has been already purchased!";
        } else if (ex.getClass() == WrongRowColumnNumberException.class) {
            message = "The number of a row or a column is out of bounds!";
        } else if (ex.getClass() == WrongTokenException.class) {
            message = "Wrong token!";
        } else if (ex.getClass() == WrongPasswordException.class) {
            message = "The password is wrong!";
            status = HttpStatus.UNAUTHORIZED;
        }
        return handleExceptionInternal(ex, new ErrorMessage(message),
                new HttpHeaders(), status, request);
    }
}
