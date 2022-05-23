package edu.nau.css.controller.handler;

import edu.nau.css.dto.error.ErrorDTO;
import edu.nau.css.exception.ObjectIsNotFolderException;
import edu.nau.css.exception.ObjectMetaDataNotFoundException;
import edu.nau.css.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlingMetaDataController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ObjectMetaDataNotFoundException.class, ObjectIsNotFolderException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> objectNotFoundException(HttpServletRequest request, ObjectMetaDataNotFoundException exception) {
        return buildResponseEntity(request, exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> registrationException(HttpServletRequest request, UserAlreadyExistsException exception) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .withTime(LocalDateTime.now())
                .withStatus(HttpStatus.UNAUTHORIZED.value())
                .withMessage(exception.getMessage())
                .withPath(request.getRequestURI())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ErrorDTO> buildResponseEntity(HttpServletRequest request, ObjectMetaDataNotFoundException exception, HttpStatus status) {
        ErrorDTO apiErrorMessage = new ErrorDTO();

        apiErrorMessage.setTime(LocalDateTime.now());
        apiErrorMessage.setStatus(status.value());
        apiErrorMessage.setMessage(exception.getMessage());
        apiErrorMessage.setPath(request.getRequestURI());
        apiErrorMessage.setKeys(exception.getErrorKeys());

        return new ResponseEntity<>(apiErrorMessage, status);
    }

}
