package fr.orsys.gamesreviews.controller;

import fr.orsys.gamesreviews.exception.RecordAlreadyExistException;
import fr.orsys.gamesreviews.exception.RecordNotFoundException;
import lombok.Getter;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ResponseBody
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RuntimeException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RecordAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleRecordAlreadyExistsException(RuntimeException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({IllegalArgumentException.class, PropertyReferenceException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentsException(RuntimeException e) {
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(RuntimeException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        String message = "Validation failed";
        return new ResponseEntity<>(new ValidationResponse(status, message, details), status);
    }

    @Getter
    private static class ErrorResponse {

        protected final LocalDateTime dateTime;
        protected final int code;
        protected final String status;
        protected final String message;

        public ErrorResponse(HttpStatus httpStatus, String message) {
            this.dateTime = LocalDateTime.now();
            this.code = httpStatus.value();
            this.status = httpStatus.name();
            this.message = message;
        }

    }

    @Getter
    private static class ValidationResponse extends ErrorResponse {

        private final List<String> details;

        public ValidationResponse(HttpStatus httpStatus, String message, List<String> details) {
            super(httpStatus, message);
            this.details = details;
        }

    }

}
