package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductCategory;
import codin.msbackendcore.catalog.domain.services.productcategory.ProductCategoryDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductCategoryRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductCategoryDomainServiceImpl implements ProductCategoryDomainService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryDomainServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Transactional
    @Override
    public ProductCategory createProductCategory(UUID tenantId, Product product, Category category) {

        if (productCategoryRepository.existsByTenantIdAndProductAndCategory(tenantId, product, category))
            throw new BadRequestException("error.already_exist", new String[]{tenantId.toString()}, "tenantId");

        var productCategory = ProductCategory.builder()
                .tenantId(tenantId)
                .product(product)
                .category(category)
                .build();

        return productCategoryRepository.save(productCategory);
    }
}
