package codin.msbackendcore.catalog.domain.model.entities;

import codin.msbackendcore.catalog.domain.model.valueobjects.ProductStatus;
import codin.msbackendcore.shared.domain.exceptions.ServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "products", schema = "catalog")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @NotBlank
    private String name;

    @Column(columnDefinition = "citext", unique = true, nullable = false)
    private String slug;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "has_variants", nullable = false)
    private boolean hasVariants;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "meta", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> meta = new HashMap<>();

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductCategory> categories = new ArrayList<>();

    public Product(ResultSet rs) {
        try {
            this.id = UUID.fromString(rs.getString("id"));
            this.tenantId = UUID.fromString(rs.getString("tenant_id"));
            this.name = rs.getString("name");
            this.slug = rs.getString("slug");
            this.description = rs.getString("description");
            this.status = ProductStatus.valueOf(rs.getString("status"));
            this.hasVariants = rs.getBoolean("has_variants");
            String metaJson = rs.getString("meta");

            if (metaJson != null) {
                ObjectMapper mapper = new ObjectMapper();
                this.meta = mapper.readValue(metaJson, new TypeReference<>() {
                });
            } else {
                this.meta = new HashMap<>();
            }

            this.createdAt = rs.getTimestamp("created_at").toInstant();
            this.updatedAt = rs.getTimestamp("updated_at").toInstant();
        } catch (SQLException | JsonProcessingException e) {
            throw new ServerErrorException("error.server_error", new String[]{e.getMessage()});
        }
    }

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.status = ProductStatus.ACTIVE;
        this.hasVariants = false;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
