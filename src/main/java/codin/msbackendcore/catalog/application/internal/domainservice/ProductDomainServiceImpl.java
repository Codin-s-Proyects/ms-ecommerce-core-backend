package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.services.ProductDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateSlug;

@Service
public class ProductDomainServiceImpl implements ProductDomainService {
    private final ProductRepository productRepository;

    public ProductDomainServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Crea producto + variantes y genera embeddings inmediatamente (seed).
     */
    @Transactional
    @Override
    public Product createProduct(UUID tenantId, Category category, Brand brand, String name, String description, Map<String, Object> meta) {

        if (Boolean.TRUE.equals(productRepository.existsByNameAndTenantId(name, tenantId)))
            throw new BadRequestException("error.already_exist", new String[]{name}, "name");

        var product = Product.builder()
                .tenantId(tenantId)
                .category(category)
                .brand(brand)
                .name(name)
                .slug(generateSlug(name))
                .description(description)
                .meta(meta)
                .build();

        return productRepository.save(product);
    }
}
