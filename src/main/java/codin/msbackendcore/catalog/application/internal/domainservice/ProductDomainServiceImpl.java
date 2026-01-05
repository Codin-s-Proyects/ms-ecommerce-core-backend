package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.valueobjects.ProductStatus;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jdbc.ProductPaginationRepository;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPage;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPaginationQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateSlug;

@Service
@Transactional
public class ProductDomainServiceImpl implements ProductDomainService {
    private final ProductRepository productRepository;
    private final ProductPaginationRepository productPaginationRepository;

    public ProductDomainServiceImpl(ProductRepository productRepository, ProductPaginationRepository productPaginationRepository) {
        this.productRepository = productRepository;
        this.productPaginationRepository = productPaginationRepository;
    }


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
    public Product updateProduct(UUID productId, UUID tenantId, Brand brand, String name, String description, Map<String, Object> meta) {
        var product = productRepository.findByIdAndTenantId(productId, tenantId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{productId.toString()}, "productId")
                );

        if (Boolean.TRUE.equals(productRepository.existsByNameAndTenantId(name, tenantId)))
            throw new BadRequestException("error.already_exist", new String[]{name}, "name");

        product.setTenantId(tenantId);
        product.setBrand(brand);
        product.setName(name);
        product.setSlug(generateSlug(name));
        product.setDescription(description);
        product.setMeta(meta);

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
    public CursorPage<Product> getProductsByCategory(UUID tenantId, UUID categoryId, CursorPaginationQuery query) {

        if (tenantId == null)
            return productPaginationRepository.findByCategory(categoryId, query);

        return productPaginationRepository.findByTenantAndCategory(tenantId, categoryId, query);
    }

    @Override
    public CursorPage<Product> getProductsByTenantId(UUID tenantId, CursorPaginationQuery query) {
        return productPaginationRepository.findByTenant(tenantId, query);
    }

    @Override
    public List<Product> getProductsByBrand(UUID tenantId, Brand brand) {
        return productRepository.findByBrandAndTenantIdAndStatus(brand, tenantId, ProductStatus.ACTIVE);
    }

    @Override
    public void deleteProductsByTenant(UUID tenantId) {
        productRepository.deleteAllByTenantId(tenantId);
    }

    @Override
    public Product deactivateProduct(UUID productId, UUID tenantId) {
        var product = productRepository.findByIdAndTenantId(productId, tenantId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{productId.toString()}, "productId")
                );

        product.setStatus(ProductStatus.ARCHIVED);

        return productRepository.save(product);
    }
}
