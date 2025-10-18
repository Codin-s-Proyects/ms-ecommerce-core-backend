package codin.msbackendcore.catalog.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "attribute_values", schema = "catalog")
public class AttributeValue {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @Column(columnDefinition = "text", nullable = false, unique = true)
    private String value;

    @Column(columnDefinition = "text")
    private String label;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }
}
