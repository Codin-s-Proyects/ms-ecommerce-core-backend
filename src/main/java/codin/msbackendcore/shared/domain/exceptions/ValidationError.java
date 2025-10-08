package codin.msbackendcore.shared.domain.exceptions;

import codin.msbackendcore.shared.domain.model.exceptions.ApiError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationError extends ApiError {

    private String resource;

    public ValidationError(int httpStatus, String message, String resource) {
        super("validation_error", httpStatus, message);
        this.resource = resource;
    }
}
