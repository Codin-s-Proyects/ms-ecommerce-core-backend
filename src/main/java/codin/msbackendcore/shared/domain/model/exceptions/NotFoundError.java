package codin.msbackendcore.shared.domain.model.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundError extends ApiError {
    private String resource;

    public NotFoundError(int httpStatus, String message, String resource) {
        super("not_found_error", httpStatus, message);
        this.resource = resource;
    }
}
