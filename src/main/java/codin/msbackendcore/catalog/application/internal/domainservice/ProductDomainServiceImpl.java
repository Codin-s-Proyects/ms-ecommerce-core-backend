package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateSlug;

@Service
public class ProductDomainServiceImpl implements ProductDomainService {
    private final ProductRepository productRepository;

    public ProductDomainServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Transactional
    @Override
    public Product createProduct(UUID tenantId, Brand brand, String name, String description, Map<String, Object> meta) {

        if (Boolean.TRUE.equals(productRepository.existsByNameAndTenantId(name, tenantId)))
            throw new BadRequestException("error.already_exist", new String[]{name}, "name");

        var product = Product.builder()
                .tenantId(tenantId)
                .brand(brand)
                .name(name)
                .slug(generateSlug(name))
                .description(description)
                .meta(meta)
                .build();

        return productRepository.save(product);
    }

    @Override
    public void updateHasVariant(UUID productId, boolean hasVariant) {
        var product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{productId.toString()}, "productId")
                );

        product.setHasVariants(hasVariant);

        productRepository.save(product);
    }

    @Override
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{productId.toString()}, "productId")
                );
    }

    @Override
    public List<Product> getProductsByCategory(UUID tenantId, Category category) {
        return productRepository.findByCategoryAndTenantId(category.getId(), tenantId);
    }

    @Override
    public List<Product> getProductsByBrand(UUID tenantId, Brand brand) {
        return productRepository.findByBrandAndTenantId(brand, tenantId);
    }
}
