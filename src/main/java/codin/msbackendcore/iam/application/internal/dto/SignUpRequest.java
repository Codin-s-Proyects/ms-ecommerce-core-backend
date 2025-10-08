package codin.msbackendcore.iam.application.internal.dto;

import codin.msbackendcore.iam.domain.model.commands.SignUpCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record SignUpRequest(
        @NotNull UUID tenantId,
        @NotBlank String userType,
        @NotBlank String type,
        @NotBlank String identifier,
        @Size(min = 6, max = 100) String secretHash,
        @NotBlank String role
) {
    public SignUpCommand toCommand() {
        return new SignUpCommand(
                tenantId,
                userType,
                type,
                identifier,
                secretHash,
                role
        );
    }
}