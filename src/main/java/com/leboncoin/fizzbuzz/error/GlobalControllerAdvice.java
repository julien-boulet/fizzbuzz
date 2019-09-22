package com.leboncoin.fizzbuzz.error;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

/**
 * Controller advice to translate the server side exceptions to client-friendly ResponseEntity
 */
@ControllerAdvice
@AllArgsConstructor
@Log4j2
public class GlobalControllerAdvice {

    public static final String OTHER_ERROR = "error.internal";
    public static final String BINDING_ERROR = "error.binding";

    private final MessageSource messageSource;

    /**
     * Handle binding error
     *
     * @return bad request with explicative message
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseEntity<Object> handleBindExceptions(BindException exception) {
        final String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map((FieldError error) ->
                        String.format("'%s=%s' %s", error.getField(), error.getRejectedValue(), error.getDefaultMessage())
                ).collect(Collectors.joining(", "));

        log.error(exception);

        return new ResponseEntity<>(getLocalizedMessageFromErrorCode(BINDING_ERROR, new Object[]{message}), HttpStatus.BAD_REQUEST);

    }

    /**
     * Handle IllegalArgument error
     *
     * @return internal server error with non explicative message because
     * it is an internal error
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<Object> handleIllegalArgumentExceptions(IllegalArgumentException exception) {
        log.error(exception);
        return new ResponseEntity<>(getLocalizedMessageFromErrorCode(OTHER_ERROR, new Object[]{}), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle all other errors types
     *
     * @return internal server error
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleOtherExceptions(Exception exception) {
        log.error(exception);
        return new ResponseEntity<>(getLocalizedMessageFromErrorCode(OTHER_ERROR, new Object[]{}), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get the correspondent localized message for an error code
     *
     * @param errorCode error that will be used for search
     * @param arguments parameters that will be used when parsing the message
     * @return the localized message if found
     */
    private String getLocalizedMessageFromErrorCode(String errorCode, Object[] arguments) {
        return messageSource.getMessage(errorCode, arguments, LocaleContextHolder.getLocale());
    }
}
