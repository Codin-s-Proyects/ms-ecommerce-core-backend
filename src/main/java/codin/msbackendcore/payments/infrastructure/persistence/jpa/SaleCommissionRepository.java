package codin.msbackendcore.payments.infrastructure.persistence.jpa;

import codin.msbackendcore.payments.domain.model.entities.SaleCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SaleCommissionRepository extends JpaRepository<SaleCommission, UUID> {
    boolean existsByOrderId(UUID orderId);
    List<SaleCommission> findAllByTenantId(UUID tenantId);
    @Query("""
                SELECT sc FROM SaleCommission sc
                WHERE sc.tenantId = :tenantId
                  AND sc.payoutStatus != 'PENDING'
            """)
    List<SaleCommission> findUnpaidSaleCommissionsByTenantId(UUID tenantId);
}
