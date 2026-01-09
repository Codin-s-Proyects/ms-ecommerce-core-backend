package codin.msbackendcore.core.application.internal.outboundservices;

import codin.msbackendcore.catalog.interfaces.acl.CatalogContextFacade;
import codin.msbackendcore.core.application.internal.dto.CatalogEmbeddingDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("ExternalCatalogServiceForCore")
public class ExternalCatalogService {
    private final CatalogContextFacade catalogContextFacade;

    public ExternalCatalogService(CatalogContextFacade catalogContextFacade) {
        this.catalogContextFacade = catalogContextFacade;
    }


    @Transactional(readOnly = true)
    public List<CatalogEmbeddingDto> getProductVariantIdsByProductId(UUID productId, UUID tenantId) {
        var catalogEmbeddingResponse = catalogContextFacade.getEmbeddingCatalogByProductId(productId, tenantId);

        return catalogEmbeddingResponse.stream()
                .map(pv ->
                        new CatalogEmbeddingDto(
                                pv.tenantId(),
                                pv.variantId(),
                                pv.productName(),
                                pv.categoryName(),
                                pv.brandName(),
                                pv.productDescription(),
                                pv.variantName(),
                                pv.variantAttributes()
                        )
                )
                .toList();
    }


}
