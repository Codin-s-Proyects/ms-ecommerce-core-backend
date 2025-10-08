package codin.msbackendcore.iam.application.internal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SignInRequest(
        @NotNull UUID tenantId,
        @NotBlank String identifier,
        @NotBlank String password
) {}