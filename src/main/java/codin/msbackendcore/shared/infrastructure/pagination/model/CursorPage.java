package codin.msbackendcore.shared.infrastructure.pagination.model;

import java.util.List;

public record CursorPage<T>(
        List<T> data,
        String nextCursor,
        boolean hasMore,
        Long totalApprox
) {}
