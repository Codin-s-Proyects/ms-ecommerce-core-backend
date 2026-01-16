package codin.msbackendcore.catalog.domain.services.productvariant;

import codin.msbackendcore.catalog.application.internal.dto.ProductVariantDetailDto;
import codin.msbackendcore.catalog.domain.model.queries.productvariant.GetProductVariantByProductAndTenantIdQuery;
import codin.msbackendcore.catalog.interfaces.dto.productvariant.ProductVariantDetailResponse;

public interface ProductVariantQueryService {
    ProductVariantDetailResponse handle(GetProductVariantByProductAndTenantIdQuery query);
}
