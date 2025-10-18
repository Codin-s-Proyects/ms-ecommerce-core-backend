package codin.msbackendcore.core.interfaces.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePromptRequest(
        @NotBlank String value
) {}
