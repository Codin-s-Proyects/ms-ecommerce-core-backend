package codin.msbackendcore.shared.infrastructure.errorhandling;

import codin.msbackendcore.shared.domain.exceptions.*;
import codin.msbackendcore.shared.domain.model.exceptions.*;
import codin.msbackendcore.shared.infrastructure.utils.MessageUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageUtils messageUtils;

    public GlobalExceptionHandler(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(BadRequestException ex, Locale locale) {
        String transformedMessage = messageUtils.getMessage(ex.getMessageKey(), locale, ex.getArgs());

        ValidationError error = new ValidationError(
                HttpStatus.BAD_REQUEST.value(),
                transformedMessage,
                ex.getResourceName()
        );
        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(error)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(NotFoundException ex, Locale locale) {
        String transformedMessage = messageUtils.getMessage(ex.getMessageKey(), locale, ex.getArgs());

        NotFoundError error = new NotFoundError(
                HttpStatus.NOT_FOUND.value(),
                transformedMessage,
                ex.getResourceName()
        );
        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(error)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticatedException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(AuthenticatedException ex, Locale locale) {
        String transformedMessage = messageUtils.getMessage(ex.getMessageKey(), locale, ex.getArgs());

        Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());
        logger.warning("Authentication error: " + ex.getMessage());

        AuthenticationError error = new AuthenticationError(
                HttpStatus.UNAUTHORIZED.value(),
                transformedMessage,
                ex.getResourceName()
        );
        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(error)), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(ServerErrorException ex, Locale locale) {
        String transformedMessage = messageUtils.getMessage(ex.getMessageKey(), locale, ex.getArgs());

        ServerError error = new ServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                transformedMessage,
                LocalTime.now().toString()
        );
        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(error)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, Locale locale) {
        List<ApiError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> toValidationError(fieldError, locale))
                .collect(Collectors.toList());

        ex.getBindingResult().getGlobalErrors()
                .forEach(globalError -> {
                    String msg = resolveMessage(globalError.getDefaultMessage(), locale, globalError.getArguments());
                    ValidationError ve = new ValidationError(HttpStatus.BAD_REQUEST.value(), msg, globalError.getObjectName());
                    errors.add(ve);
                });

        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, Locale locale) {
        List<ApiError> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> toValidationError(violation, locale))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex, Locale locale) {
        List<ApiError> errors = ex.getFieldErrors()
                .stream()
                .map(fieldError -> toValidationError(fieldError, locale))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    private ValidationError toValidationError(FieldError fieldError, Locale locale) {
        String resolvedMessage = resolveMessage(fieldError.getDefaultMessage(), locale, fieldError.getArguments());
        String resource = fieldError.getField();
        return new ValidationError(HttpStatus.BAD_REQUEST.value(), resolvedMessage, resource);
    }

    private ValidationError toValidationError(ConstraintViolation<?> violation, Locale locale) {
        String propertyPath = violation.getPropertyPath() != null ? violation.getPropertyPath().toString() : null;
        String resolvedMessage = resolveMessage(violation.getMessage(), locale);
        return new ValidationError(HttpStatus.BAD_REQUEST.value(), resolvedMessage, propertyPath);
    }

    private String resolveMessage(String messageOrKey, Locale locale, Object... args) {
        try {
            String resolved = messageUtils.getMessage(messageOrKey, locale, args);
            if (resolved == null || resolved.isBlank() || resolved.equals(messageOrKey)) {
                return formatFallbackMessage(messageOrKey, args);
            }
            return resolved;
        } catch (Exception e) {
            return formatFallbackMessage(messageOrKey, args);
        }
    }

    private String resolveMessage(String messageOrKey, Locale locale) {
        return resolveMessage(messageOrKey, locale, new Object[]{});
    }

    private String formatFallbackMessage(String message, Object... args) {
        if (args == null || args.length == 0) return message;
        try {
            return String.format(message, args);
        } catch (Exception e) {
            return message;
        }
    }
}
