package codin.msbackendcore.catalog.infrastructure.persistence.jdbc;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.shared.infrastructure.pagination.CursorPaginationRepository;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPage;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPaginationQuery;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class ProductPaginationRepository extends CursorPaginationRepository<Product> {

    private static final String REPOSITORY_ALIAS = "p";

    public ProductPaginationRepository(NamedParameterJdbcTemplate jdbc) {
        super(jdbc);
    }

    public CursorPage<Product> findByCategory(
            UUID categoryId, CursorPaginationQuery query
    ) {
        var sql = """
                    SELECT p.*
                    FROM catalog.products p
                    JOIN catalog.product_categories pc ON pc.product_id = p.id
                    WHERE pc.category_id = :categoryId
                """;

        var filters = new HashMap<String, Object>();
        filters.put("categoryId", categoryId);

        return paginate(sql, filters, query, (rs, i) -> new Product(rs), REPOSITORY_ALIAS);
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

        return paginate(sql, filters, query, (rs, i) -> new Product(rs), REPOSITORY_ALIAS);
    }

    public CursorPage<Product> findByTenant(
            UUID tenantId, CursorPaginationQuery query
    ) {
        var sql = """
                    SELECT p.*
                    FROM catalog.products p
                    WHERE p.tenant_id = :tenantId
                """;

        var filters = new HashMap<String, Object>();
        filters.put("tenantId", tenantId);

        return paginate(sql, filters, query, (rs, i) -> new Product(rs), REPOSITORY_ALIAS);
    }

    @Override
    protected Object[] extractKeyset(Product product) {
        return new Object[]{product.getUpdatedAt().toString(), product.getId().toString()};
    }
}
