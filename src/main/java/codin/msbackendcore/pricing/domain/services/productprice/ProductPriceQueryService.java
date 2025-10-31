package codin.msbackendcore.pricing.domain.services.productprice;

import codin.msbackendcore.pricing.domain.model.entities.ProductPrice;
import codin.msbackendcore.pricing.domain.model.queries.GetAllProductPriceByProductVariantIdQuery;

import java.util.List;

public interface ProductPriceQueryService {
    List<ProductPrice> handle(GetAllProductPriceByProductVariantIdQuery query);
}
