package codin.msbackendcore.catalog.infrastructure.persistence.jpa;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {

    boolean existsByNameAndTenantIdAndProduct(String name, UUID tenantId, Product product);

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

    List<ProductVariant> findAllByProductAndTenantId(Product product, UUID tenantId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.id = :id AND pv.tenantId = :tenantId")
    Optional<ProductVariant> findByIdForUpdate(UUID id, UUID tenantId);

}