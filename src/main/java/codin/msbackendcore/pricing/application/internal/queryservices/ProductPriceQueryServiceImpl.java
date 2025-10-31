package codin.msbackendcore.pricing.application.internal.queryservices;

import codin.msbackendcore.pricing.domain.model.entities.ProductPrice;
import codin.msbackendcore.pricing.domain.model.queries.GetAllProductPriceByProductVariantIdQuery;
import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceDomainService;
import codin.msbackendcore.pricing.domain.services.productprice.ProductPriceQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPriceQueryServiceImpl implements ProductPriceQueryService {

    private final ProductPriceDomainService productPriceDomainService;

    public ProductPriceQueryServiceImpl(ProductPriceDomainService productPriceDomainService) {
        this.productPriceDomainService = productPriceDomainService;
    }

    @Override
    public List<ProductPrice> handle(GetAllProductPriceByProductVariantIdQuery query) {
        return productPriceDomainService.getProductPricesByProductVariantId(query.tenantId(), query.productVariantId());
    }
}
