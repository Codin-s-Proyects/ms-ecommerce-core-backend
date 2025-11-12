package codin.msbackendcore.shared.infrastructure.pagination.model;

import java.util.Arrays;
import java.util.Objects;

public record CursorToken(
        String sort,
        Object[] keyset,
        Object metadata,
        int version
) {
    @Override
    public String toString() {
        return "CursorToken{" +
                "sort='" + sort + '\'' +
                ", keyset=" + Arrays.toString(keyset) +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CursorToken that)) return false;
        return version == that.version &&
                Objects.equals(sort, that.sort) &&
                Arrays.equals(keyset, that.keyset);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sort, version);
        result = 31 * result + Arrays.hashCode(keyset);
        return result;
    }
}