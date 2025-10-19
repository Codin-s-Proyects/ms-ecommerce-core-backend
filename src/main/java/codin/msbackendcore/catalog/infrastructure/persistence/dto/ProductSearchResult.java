package codin.msbackendcore.catalog.infrastructure.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductSearchResult {
    private UUID variantId;
    private String variantName;
    private String variantImageUrl;
    private String sku;
    private String productName;
    private String productDescription;
    private BigDecimal price;
}