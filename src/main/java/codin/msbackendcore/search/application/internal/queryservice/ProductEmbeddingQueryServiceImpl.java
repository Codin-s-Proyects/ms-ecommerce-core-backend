package codin.msbackendcore.search.application.internal.queryservice;

import codin.msbackendcore.search.application.internal.dto.SemanticSearchDto;
import codin.msbackendcore.search.application.internal.outboundservices.acl.ExternalCatalogService;
import codin.msbackendcore.search.application.internal.outboundservices.acl.ExternalCoreService;
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
    private final ExternalCoreService externalCoreService;

    public ProductEmbeddingQueryServiceImpl(ProductEmbeddingDomainService productEmbeddingDomainService, ExternalCatalogService externalCatalogService, ExternalPricingService externalPricingService, ExternalCoreService externalCoreService) {
        this.productEmbeddingDomainService = productEmbeddingDomainService;
        this.externalCatalogService = externalCatalogService;
        this.externalPricingService = externalPricingService;
        this.externalCoreService = externalCoreService;
    }

    @Override
    public CompletableFuture<List<SemanticSearchDto>> handle(SemanticSearchQuery query) {
        var productEmbeddingList = productEmbeddingDomainService.semanticSearch(
                query.tenantId(),
                query.query(),
                query.limit()
        );

        return productEmbeddingList.thenCompose(pel -> {
            List<CompletableFuture<SemanticSearchDto>> futures = pel.stream()
                    .map(pe -> CompletableFuture.supplyAsync(() -> {
                        var productVariantDto = externalCatalogService.getVariantById(pe.getProductVariantId());
                        var productDto = externalCatalogService.getProductById(productVariantDto.productId());
                        var mediaAssetsDto = externalCoreService.getMediaAssetsByVariantId(pe.getTenantId(), pe.getProductVariantId());
                        var productPriceListDto = externalPricingService.getProductPriceByVariantId(pe.getTenantId(), pe.getProductVariantId());

                        return new SemanticSearchDto(
                                productDto,
                                productVariantDto,
                                mediaAssetsDto,
                                productPriceListDto
                        );
                    }))
                    .toList();

            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> futures.stream()
                            .map(CompletableFuture::join)
                            .toList());
        });
    }
}
