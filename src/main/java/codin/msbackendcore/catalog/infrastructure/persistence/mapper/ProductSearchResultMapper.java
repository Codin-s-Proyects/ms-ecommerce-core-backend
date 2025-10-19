package codin.msbackendcore.catalog.infrastructure.persistence.mapper;

import codin.msbackendcore.catalog.infrastructure.persistence.dto.ProductSearchResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ProductSearchResultMapper {

    public List<ProductSearchResult> map(List<Object[]> results) {
        List<ProductSearchResult> list = new ArrayList<>();
        for (Object[] row : results) {
            list.add(new ProductSearchResult(
                    (UUID) row[0],                          // variant_id
                    (String) row[1],                        // variant_name
                    (String) row[2],                        // variant_image_url
                    (String) row[3],                        // sku
                    (String) row[4],                        // product_name
                    (String) row[5],                        // product_description
                    row[6] != null ? (BigDecimal) row[6] : BigDecimal.ZERO, // retail_price
                    row[7] != null ? (BigDecimal) row[7] : BigDecimal.ZERO // wholesale_price
            ));
        }
        return list;
    }
}