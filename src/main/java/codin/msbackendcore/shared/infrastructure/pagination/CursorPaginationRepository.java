package codin.msbackendcore.shared.infrastructure.pagination;

import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPage;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorPaginationQuery;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorSort;
import codin.msbackendcore.shared.infrastructure.pagination.model.CursorToken;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class CursorPaginationRepository<T> {

    protected final NamedParameterJdbcTemplate jdbc;

    protected CursorPaginationRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    protected CursorPage<T> paginate(
            String baseSql,
            Map<String, Object> filters,
            CursorPaginationQuery query,
            RowMapper<T> mapper,
            String alias
    ) {
        var limit = query.effectiveLimit();
        var sort = CursorSort.parse(query.effectiveSort());

        OffsetDateTime lastUpdated = null;
        UUID lastId = null;

        if (query.cursor() != null && !query.cursor().isBlank()) {
            var token = CursorCodec.decode(query.cursor());
            if (token.sort().equals(sort.toString())) {
                var keyset = token.keyset();
                lastUpdated = OffsetDateTime.parse(keyset[0].toString());
                lastId = UUID.fromString(keyset[1].toString());
            }
        }

        var params = new HashMap<>(filters);
        var comparator = new StringBuilder();

        boolean desc = sort.isDesc();

        if (lastUpdated != null && lastId != null) {
            if (desc) {
                comparator.append(String.format(
                        " AND (%s.updated_at < :lastUpdated OR (%s.updated_at = :lastUpdated AND %s.id < :lastId)) ",
                        alias, alias, alias
                ));
            } else {
                comparator.append(String.format(
                        " AND (%s.updated_at > :lastUpdated OR (%s.updated_at = :lastUpdated AND %s.id > :lastId)) ",
                        alias, alias, alias
                ));
            }
            params.put("lastUpdated", Timestamp.from(lastUpdated.toInstant()));
            params.put("lastId", lastId);
        }

        String orderDir = desc ? "DESC" : "ASC";
        String orderBy = String.format(
                " ORDER BY %s.updated_at %s, %s.id %s",
                alias, orderDir, alias, orderDir
        );

        var sql = baseSql + comparator + orderBy + " LIMIT :limitPlusOne";
        params.put("limitPlusOne", limit + 1);

        List<T> rows = jdbc.query(sql, params, mapper);

        boolean hasMore = rows.size() > limit;
        var slice = hasMore ? rows.subList(0, limit) : rows;

        String nextCursor = null;
        if (hasMore && !slice.isEmpty()) {
            var last = slice.get(slice.size() - 1);
            var keyset = extractKeyset(last);

            nextCursor = CursorCodec.encode(new CursorToken(
                    sort.toString(),
                    new Object[]{keyset[0].toString(), keyset[1].toString()},
                    null,
                    1
            ));
        }

        var countSql = "SELECT COUNT(*) FROM (" + baseSql + ") AS count_query";
        Long totalApprox = jdbc.queryForObject(countSql, filters, Long.class);

        return new CursorPage<>(slice, nextCursor, hasMore, totalApprox);
    }

    protected abstract Object[] extractKeyset(T entity);
}