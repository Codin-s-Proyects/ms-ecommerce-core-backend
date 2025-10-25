package codin.msbackendcore.pricing.interfaces.rest;

import codin.msbackendcore.pricing.domain.services.discount.DiscountCommandService;
import codin.msbackendcore.pricing.interfaces.dto.discount.DiscountCreateRequest;
import codin.msbackendcore.pricing.interfaces.dto.discount.DiscountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pricing/discount")
@Tag(name = "Discount Controller", description = "Operaciones relacionadas con descuentos")
public class DiscountController {

    private final DiscountCommandService discountCommandService;

    public DiscountController(DiscountCommandService discountCommandService) {
        this.discountCommandService = discountCommandService;
    }

    @Operation(summary = "Crear un nuevo descuento")
    @PostMapping
    public ResponseEntity<DiscountResponse> createDiscount(@Valid @RequestBody DiscountCreateRequest req) {

        var command = req.toCommand();

        var saved = discountCommandService.handle(command);

        var response = new DiscountResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPercentage(),
                saved.getStartsAt(),
                saved.getEndsAt()
        );

        return ResponseEntity.status(201).body(response);
    }
}

