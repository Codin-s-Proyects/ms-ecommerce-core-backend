package codin.msbackendcore.search.application.internal.queryservice;

import codin.msbackendcore.search.application.internal.dto.SemanticSearchDto;
import codin.msbackendcore.search.domain.model.queries.SemanticSearchQuery;
import codin.msbackendcore.search.domain.model.valueobjects.SemanticSearchMode;
import codin.msbackendcore.search.domain.services.ProductEmbeddingDomainService;
import codin.msbackendcore.search.domain.services.ProductEmbeddingQueryService;
import codin.msbackendcore.search.infrastructure.persistence.mapper.SemanticSearchMapper;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.isValidEnum;

@Service
public class ProductEmbeddingQueryServiceImpl implements ProductEmbeddingQueryService {

    private final ProductEmbeddingDomainService productEmbeddingDomainService;
    private final SemanticSearchMapper semanticSearchMapper;

    public ProductEmbeddingQueryServiceImpl(ProductEmbeddingDomainService productEmbeddingDomainService, SemanticSearchMapper semanticSearchMapper) {
        this.productEmbeddingDomainService = productEmbeddingDomainService;
        this.semanticSearchMapper = semanticSearchMapper;

    }

    @Override
    public CompletableFuture<List<SemanticSearchDto>> handle(SemanticSearchQuery query) {

        if (!isValidEnum(SemanticSearchMode.class, query.mode())) {
            throw new BadRequestException("error.bad_request", new String[]{query.mode()}, "mode");
        }

        var productEmbeddingList = productEmbeddingDomainService.semanticSearch(
                query.tenantId(),
                query.query(),
                query.limit(),
                SemanticSearchMode.valueOf(query.mode()),
                query.distanceThreshold()
        );

        return productEmbeddingList.thenCompose(embeddings  -> {
            if (embeddings .isEmpty()) {
                return CompletableFuture.completedFuture(List.of());
            }

            Map<UUID, Integer> ranking = new HashMap<>();
            List<UUID> variantIds = new ArrayList<>();

            for (int i = 0; i < embeddings.size(); i++) {
                variantIds.add(embeddings.get(i).productVariantId());
                ranking.put(embeddings.get(i).productVariantId(), i);
            }

            List<String> rawJson =
                    productEmbeddingDomainService.findSemanticDetails(
                            query.tenantId(),
                            variantIds.toArray(UUID[]::new)
                    );

            List<SemanticSearchDto> result = new ArrayList<>(rawJson.stream()
                    .map(semanticSearchMapper::toDto)
                    .toList());

            result.sort(Comparator.comparingInt(
                    dto -> ranking.get(dto.productVariant().id())
            ));

            return CompletableFuture.completedFuture(result);
        });
    }
}
