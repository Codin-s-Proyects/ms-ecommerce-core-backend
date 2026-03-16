package codin.msbackendcore.ordering.interfaces.rest.controller;

import codin.msbackendcore.ordering.domain.model.entities.UserShippingAddress;
import codin.msbackendcore.ordering.domain.model.queries.GetUserShippingAddressByUserQuery;
import codin.msbackendcore.ordering.domain.services.usershippingaddress.UserShippingAddressCommandService;
import codin.msbackendcore.ordering.domain.services.usershippingaddress.UserShippingAddressQueryService;
import codin.msbackendcore.ordering.interfaces.dto.usershippingaddress.UserShippingAddressCreateRequest;
import codin.msbackendcore.ordering.interfaces.dto.usershippingaddress.UserShippingAddressResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/ordering/user-shipping-address")
@Tag(name = "User Shipping Address Controller", description = "Operaciones relacionadas con las direcciones de envio de los usuarios")
public class UserShippingAddressController {

    private final UserShippingAddressCommandService userShippingAddressCommandService;
    private final UserShippingAddressQueryService userShippingAddressQueryService;

    public UserShippingAddressController(UserShippingAddressCommandService userShippingAddressCommandService, UserShippingAddressQueryService userShippingAddressQueryService) {
        this.userShippingAddressCommandService = userShippingAddressCommandService;
        this.userShippingAddressQueryService = userShippingAddressQueryService;
    }

    @Operation(summary = "Guardar direccion de envio de un usuario", description = "Guarda una direccion de envio del usuario")
    @PostMapping
    public ResponseEntity<UserShippingAddressResponse> createUserShippingAddress(@Valid @RequestBody UserShippingAddressCreateRequest req) {

        var command = req.toCommand();

        var saved = userShippingAddressCommandService.handle(command);

        var response = entityToResponse(saved);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Obtener direccion de envio de un usuario", description = "Obtiene una direccion de envio del usuario")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserShippingAddressResponse>> getUserShippingAddressByUser(@PathVariable UUID userId) {

        var query = new GetUserShippingAddressByUserQuery(userId);

        var userShippingAddressesList = userShippingAddressQueryService.handle(query);

        var listResponse = userShippingAddressesList.stream().map(this::entityToResponse).toList();

        return ResponseEntity.status(200).body(listResponse);
    }

    private UserShippingAddressResponse entityToResponse(UserShippingAddress entity) {

        return new UserShippingAddressResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getLabel(),
                entity.getDepartment(),
                entity.getProvince(),
                entity.getDistrict(),
                entity.getAddressLine(),
                entity.getReference(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getPreferredShippingProvider(),
                entity.getPreferredShippingService(),
                entity.getIsDefault(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

