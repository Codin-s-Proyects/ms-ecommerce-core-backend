package codin.msbackendcore.catalog.infrastructure.persistence.jpa;

import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, UUID> {
    Boolean existsByCodeAndTenantId(String code, UUID tenantId);
}
