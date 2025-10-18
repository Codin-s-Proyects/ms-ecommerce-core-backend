package codin.msbackendcore.search.infrastructure.persistence.jpa;

import codin.msbackendcore.search.domain.model.entities.ProductEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEmbeddingRepository extends JpaRepository<ProductEmbedding, Long> {
}
