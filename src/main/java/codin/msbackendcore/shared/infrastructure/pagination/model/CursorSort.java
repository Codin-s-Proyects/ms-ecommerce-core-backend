package codin.msbackendcore.shared.infrastructure.pagination.model;


import lombok.Getter;

import java.util.Objects;

@Getter
public class CursorSort {
    private final String field;
    private final boolean desc;

    public CursorSort(String field, boolean desc) {
        this.field = field;
        this.desc = desc;
    }

    public String getDirection() {
        return desc ? "DESC" : "ASC";
    }

    public static CursorSort parse(String sortExpression) {
        String[] parts = sortExpression.split(",");
        String primary = parts[0].trim();
        boolean desc = primary.startsWith("-");
        String field = desc ? primary.substring(1) : primary;
        return new CursorSort(field, desc);
    }

    @Override
    public String toString() {
        return (desc ? "-" : "") + field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CursorSort that)) return false;
        return desc == that.desc && Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, desc);
    }
}
