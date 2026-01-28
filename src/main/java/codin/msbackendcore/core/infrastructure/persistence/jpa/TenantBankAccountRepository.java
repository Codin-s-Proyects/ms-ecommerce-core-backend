package codin.msbackendcore.core.infrastructure.persistence.jpa;

import codin.msbackendcore.core.domain.model.entities.Tenant;
import codin.msbackendcore.core.domain.model.entities.TenantBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantBankAccountRepository extends JpaRepository<TenantBankAccount, UUID> {
}
