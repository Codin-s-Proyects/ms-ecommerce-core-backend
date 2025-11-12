package codin.msbackendcore.shared.infrastructure.pagination.model;

import org.springframework.lang.Nullable;

public record CursorPaginationQuery(
        @Nullable String cursor,
        int limit,
        @Nullable String sort
) {
    public int effectiveLimit() {
        return Math.min(Math.max(limit, 1), 200);
    }

    public String effectiveSort() {
        return (sort == null || sort.isBlank()) ? "-updatedAt,id" : sort;
    }
}