package codin.msbackendcore.catalog.infrastructure.persistence.jpa;

import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
    boolean existsByTenantIdAndProductAndCategory(UUID tenantId, Product product, Category category);
    Optional<ProductCategory> findByTenantIdAndId(UUID tenantId, UUID productCategoryId);
    @Query("""
        SELECT pc.category.id
        FROM ProductCategory pc
        WHERE pc.tenantId = :tenantId
          AND pc.product.id = :productId
    """)
    Set<UUID> findProductCategoryByProductId(UUID tenantId, UUID productId);
    void deleteAllByTenantIdAndProductIdAndCategoryIdIn(UUID tenantId, UUID productId, Set<UUID> categoryIds);
}
