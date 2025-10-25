package codin.msbackendcore.pricing.interfaces.dto.productdiscount;

import codin.msbackendcore.pricing.domain.model.commands.CreateProductDiscountCommand;

import java.util.UUID;

public record ProductDiscountCreateRequest(
        UUID tenantId,
        UUID productVariantId,
        UUID discountId
) {
    public CreateProductDiscountCommand toCommand() {
        return new CreateProductDiscountCommand(
                tenantId,
                productVariantId,
                discountId
        );
    }
}
