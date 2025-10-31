package codin.msbackendcore.catalog.infrastructure.persistence.jpa;

import codin.msbackendcore.catalog.domain.model.entities.Attribute;
import codin.msbackendcore.catalog.domain.model.entities.Category;
import codin.msbackendcore.catalog.domain.model.entities.CategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, UUID> {
    boolean existsByCategoryAndAttributeAndTenantId(Category category, Attribute attribute, UUID tenantId);
    List<CategoryAttribute> findAllByTenantIdAndCategory(UUID tenantId, Category category);
}
