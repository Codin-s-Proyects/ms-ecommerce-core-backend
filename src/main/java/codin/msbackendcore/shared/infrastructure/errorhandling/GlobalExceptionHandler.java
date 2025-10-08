package codin.msbackendcore.shared.infrastructure.errorhandling;

import codin.msbackendcore.shared.domain.exceptions.*;
import codin.msbackendcore.shared.domain.model.exceptions.AuthenticationError;
import codin.msbackendcore.shared.domain.model.exceptions.ErrorResponse;
import codin.msbackendcore.shared.domain.model.exceptions.NotFoundError;
import codin.msbackendcore.shared.domain.model.exceptions.ServerError;
import codin.msbackendcore.shared.infrastructure.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Locale;
import java.util.logging.Logger;

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
}

