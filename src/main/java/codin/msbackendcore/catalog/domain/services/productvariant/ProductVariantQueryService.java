package codin.msbackendcore.catalog.domain.services.productvariant;

import codin.msbackendcore.catalog.application.internal.dto.ProductVariantDetailDto;
import codin.msbackendcore.catalog.domain.model.queries.productvariant.GetProductVariantByProductAndTenantIdQuery;

public interface ProductVariantQueryService {
    ProductVariantDetailDto handle(GetProductVariantByProductAndTenantIdQuery query);
}
