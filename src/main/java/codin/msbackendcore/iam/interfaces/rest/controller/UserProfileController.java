package codin.msbackendcore.iam.interfaces.rest.controller;

import codin.msbackendcore.iam.domain.model.entities.UserProfile;
import codin.msbackendcore.iam.domain.model.queries.GetUserProfileByUserQuery;
import codin.msbackendcore.iam.domain.services.userprofile.UserProfileCommandService;
import codin.msbackendcore.iam.domain.services.userprofile.UserProfileQueryService;
import codin.msbackendcore.iam.interfaces.dto.userprofile.UserProfileCreateRequest;
import codin.msbackendcore.iam.interfaces.dto.userprofile.UserProfileResponse;
import codin.msbackendcore.ordering.domain.model.queries.GetUserShippingAddressByUserQuery;
import codin.msbackendcore.ordering.interfaces.dto.order.response.*;
import codin.msbackendcore.ordering.interfaces.dto.usershippingaddress.UserShippingAddressResponse;
import codin.msbackendcore.shared.domain.exceptions.ServerErrorException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/iam/user-profile")
@Tag(name = "User Profile Controller", description = "Operaciones relacionadas con el perfil del usuario")
public class UserProfileController {

    private final UserProfileCommandService userProfileCommandService;
    private final UserProfileQueryService userProfileQueryService;

    public UserProfileController(UserProfileCommandService userProfileCommandService, UserProfileQueryService userProfileQueryService) {
        this.userProfileCommandService = userProfileCommandService;
        this.userProfileQueryService = userProfileQueryService;
    }

    @Operation(summary = "Crear una nueva orden", description = "Crea una nueva orden en el sistema")
    @PostMapping
    public ResponseEntity<UserProfileResponse> createOrder(@Valid @RequestBody UserProfileCreateRequest req) {

        var command = req.toCommand();

        var saved = userProfileCommandService.handle(command)
                .orElseThrow(() -> new ServerErrorException("error.server_error", new String[]{"Something went wrong"}));

        var response = entityToResponse(saved);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Obtener la informacion del perfil de un usuario", description = "Obtiene el perfil de un usuario")
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfileByUser(@PathVariable UUID userId) {

        var query = new GetUserProfileByUserQuery(userId);

        var userProfile = userProfileQueryService.handle(query);

        return ResponseEntity.status(200).body(entityToResponse(userProfile));
    }

    private UserProfileResponse entityToResponse(UserProfile entity) {
        return new UserProfileResponse(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTenantId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getDocumentType().name(),
                entity.getDocumentNumber(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

