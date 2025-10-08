package codin.msbackendcore.shared.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticatedException extends RuntimeException {
    private final String messageKey;
    private final Object[] args;
    private final String resourceName;

    public AuthenticatedException(String messageKey, Object[] args, String resourceName) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
        this.resourceName = resourceName;
    }
}
