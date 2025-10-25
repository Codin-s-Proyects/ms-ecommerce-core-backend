package codin.msbackendcore.search.interfaces.rest.controller;

import codin.msbackendcore.search.domain.services.ProductEmbeddingQueryService;
import codin.msbackendcore.search.interfaces.dto.SemanticSearchRequest;
import codin.msbackendcore.search.interfaces.dto.SemanticSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/search/product-embedding")
@Tag(name = "Product Embedding", description = "Operaciones relacionadas con Búsqueda Semántica de Productos")
public class ProductEmbeddingController {

    private final ProductEmbeddingQueryService productEmbeddingQueryService;

    public ProductEmbeddingController(ProductEmbeddingQueryService productEmbeddingQueryService) {
        this.productEmbeddingQueryService = productEmbeddingQueryService;
    }

    @Operation(summary = "Búsqueda semántica de productos")
    @PostMapping("/semantic-search")
    public CompletableFuture<ResponseEntity<List<SemanticSearchResponse>>> semanticSearchProduct(@Valid @RequestBody SemanticSearchRequest request) {

        var query = request.toQuery();

        return productEmbeddingQueryService.handle(query)
                .thenApply(
                        responses -> responses.stream()
                                .map(SemanticSearchResponse::fromDto)
                                .toList()
                )
                .thenApply(ResponseEntity::ok);
    }
}
