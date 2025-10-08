package codin.msbackendcore.shared.domain.model.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerError extends ApiError {
    private String timestamp;

    public ServerError(int httpStatus, String message, String timestamp) {
        super("server_error", httpStatus, message);
        this.timestamp = timestamp;
    }
}
