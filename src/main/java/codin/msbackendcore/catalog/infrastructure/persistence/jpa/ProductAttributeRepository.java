package codin.msbackendcore.catalog.infrastructure.persistence.jpa;

import codin.msbackendcore.catalog.domain.model.entities.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, UUID> {
}
