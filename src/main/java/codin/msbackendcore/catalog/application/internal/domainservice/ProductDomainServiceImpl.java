package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.services.ProductDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductRepository;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductVariantRepository;
import codin.msbackendcore.search.domain.services.ProductEmbeddingDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductDomainServiceImpl implements ProductDomainService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductEmbeddingDomainService embeddingDomainService;

    public ProductDomainServiceImpl(
            ProductRepository productRepository,
            ProductVariantRepository variantRepository,
            ProductEmbeddingDomainService embeddingDomainService) {
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
        this.embeddingDomainService = embeddingDomainService;
    }

    /**
     * Crea producto + variantes y genera embeddings inmediatamente (seed).
     */
    @Transactional
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
