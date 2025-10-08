package codin.msbackendcore.shared.domain.model.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationError extends ApiError {
    private String resource;

    public AuthenticationError(int httpStatus, String message, String resource) {
        super("authentication_error", httpStatus, message);
        this.resource = resource;
    }
}