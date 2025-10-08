package codin.msbackendcore.iam.application.internal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotNull String tenantId,
        @NotBlank String userType,
        @Email String email,
        @NotBlank @Size(min = 6, max = 100) String password,
        boolean isPrimary
) {}