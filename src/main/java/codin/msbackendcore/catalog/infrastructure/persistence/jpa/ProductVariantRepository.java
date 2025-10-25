package codin.msbackendcore.catalog.infrastructure.persistence.jpa;

import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {

    boolean existsByNameAndTenantId(String name, UUID tenantId);

    @Query(value = """
            SELECT EXISTS(
              SELECT 1
              FROM catalog.product_variants
              WHERE product_id = :productId
                AND tenant_id = :tenantId
                AND attributes = CAST(:attributes AS jsonb)
            )
            """, nativeQuery = true)
    boolean existsByProductAndAttributes(
            @Param("tenantId") UUID tenantId,
            @Param("productId") UUID productId,
            @Param("attributes") String attributesJson
    );

}