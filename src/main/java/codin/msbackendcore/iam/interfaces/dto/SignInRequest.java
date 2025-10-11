package codin.msbackendcore.iam.interfaces.dto;

import codin.msbackendcore.iam.domain.model.commands.SignInCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SignInRequest(
        @NotNull UUID tenantId,
        @NotBlank String identifier,
        @NotBlank String password,
        @NotBlank String deviceInfo,
        @NotBlank String ip
) {
    public SignInCommand toCommand() {
        return new SignInCommand(
                tenantId,
                identifier,
                password,
                deviceInfo,
                ip
        );
    }
}