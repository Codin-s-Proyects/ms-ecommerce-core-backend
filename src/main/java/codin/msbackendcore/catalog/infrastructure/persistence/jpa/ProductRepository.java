package codin.msbackendcore.catalog.infrastructure.persistence.jpa;

import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Boolean existsByNameAndTenantId(String name, UUID tenantId);

    @Query(value = """
            SELECT p.*
            FROM catalog.products p
            JOIN catalog.product_categories pc ON pc.product_id = p.id
            WHERE pc.category_id = :categoryId
              AND p.tenant_id = :tenantId
            """, nativeQuery = true)
    List<Product> findByCategoryAndTenantId(
            @Param("categoryId") UUID categoryId,
            @Param("tenantId") UUID tenantId
    );

    List<Product> findByBrandAndTenantId(Brand brand, UUID tenantId);
}