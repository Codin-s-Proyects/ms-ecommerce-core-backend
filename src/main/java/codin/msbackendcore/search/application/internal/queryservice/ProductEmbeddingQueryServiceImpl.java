package codin.msbackendcore.search.application.internal.queryservice;

import codin.msbackendcore.search.application.internal.dto.SemanticSearchDto;
import codin.msbackendcore.search.application.internal.outboundservices.acl.ExternalCatalogService;
import codin.msbackendcore.search.application.internal.outboundservices.acl.ExternalPricingService;
import codin.msbackendcore.search.domain.model.queries.SemanticSearchQuery;
import codin.msbackendcore.search.domain.services.ProductEmbeddingDomainService;
import codin.msbackendcore.search.domain.services.ProductEmbeddingQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductEmbeddingQueryServiceImpl implements ProductEmbeddingQueryService {

    private final ProductEmbeddingDomainService productEmbeddingDomainService;
    private final ExternalCatalogService externalCatalogService;
    private final ExternalPricingService externalPricingService;

    public ProductEmbeddingQueryServiceImpl(ProductEmbeddingDomainService productEmbeddingDomainService, ExternalCatalogService externalCatalogService, ExternalPricingService externalPricingService) {
        this.productEmbeddingDomainService = productEmbeddingDomainService;
        this.externalCatalogService = externalCatalogService;
        this.externalPricingService = externalPricingService;
    }

    @Override
    public CompletableFuture<List<SemanticSearchDto>> handle(SemanticSearchQuery query) {
        var productEmbeddingList = productEmbeddingDomainService.semanticSearch(
                query.tenantId(),
                query.query(),
                query.limit()
        );

        return productEmbeddingList
                .thenApply(pel -> pel.stream()
                        .map(pe -> {
                            var productVariantDto = externalCatalogService.getVariantById(pe.getProductVariantId());
                            var productDto = externalCatalogService.getProductById(productVariantDto.productId());
                            var productPriceListDto = externalPricingService.getProductPriceByVariantId(pe.getTenantId(), pe.getProductVariantId());

                            return new SemanticSearchDto(
                                    productDto,
                                    productVariantDto,
                                    productPriceListDto
                            );
                        })
                        .toList());
    }
}
