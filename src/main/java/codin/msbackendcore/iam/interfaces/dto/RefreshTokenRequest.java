package codin.msbackendcore.iam.interfaces.dto;

import codin.msbackendcore.iam.domain.model.commands.RefreshTokenCommand;
import codin.msbackendcore.iam.domain.model.commands.SignInCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record RefreshTokenRequest(
        @NotBlank String refreshToken,
        @NotNull UUID userId,
        @NotNull UUID tenantId,
        @NotBlank String identifier,
        @NotBlank String password,
        @NotBlank String deviceInfo,
        @NotBlank
        @Pattern(
                regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$",
                message = "error.bad_request"
        )
        String ip
) {
    public RefreshTokenCommand toCommand() {
        return new RefreshTokenCommand(
                refreshToken,
                userId,
                tenantId,
                identifier,
                password,
                deviceInfo,
                ip
        );
    }
}