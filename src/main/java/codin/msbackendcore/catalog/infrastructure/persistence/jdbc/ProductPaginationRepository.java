package codin.msbackendcore.catalog.infrastructure.persistence.jdbc;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPage;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPaginationQuery;
import codin.msbackendcore.shared.infrastructure.pagination.CursorPaginationRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class ProductPaginationRepository extends CursorPaginationRepository<Product> {

    public ProductPaginationRepository(NamedParameterJdbcTemplate jdbc) {
        super(jdbc);
    }

    public CursorPage<Product> findByTenantAndCategory(
            UUID tenantId, UUID categoryId, CursorPaginationQuery query
    ) {
        var sql = """
                    SELECT p.*
                    FROM catalog.products p
                    JOIN catalog.product_categories pc ON pc.product_id = p.id
                    WHERE p.tenant_id = :tenantId AND pc.category_id = :categoryId
                """;

        var filters = new HashMap<String, Object>();
        filters.put("tenantId", tenantId);
        filters.put("categoryId", categoryId);

        return paginate(sql, filters, query, (rs, i) -> new Product(rs), "p");
    }

    @Override
    protected Object[] extractKeyset(Product product) {
        return new Object[]{product.getUpdatedAt().toString(), product.getId().toString()};
    }
}
