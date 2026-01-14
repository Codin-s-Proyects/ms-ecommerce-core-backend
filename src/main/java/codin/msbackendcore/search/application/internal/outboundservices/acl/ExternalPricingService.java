package codin.msbackendcore.search.application.internal.outboundservices.acl;

import codin.msbackendcore.pricing.interfaces.acl.PricingContextFacade;
import codin.msbackendcore.search.application.internal.dto.PriceListDto;
import codin.msbackendcore.search.application.internal.dto.ProductPriceDto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("ExternalPricingServiceForSearch")
public class ExternalPricingService {
    private final PricingContextFacade pricingContextFacade;

    public ExternalPricingService(PricingContextFacade pricingContextFacade) {
        this.pricingContextFacade = pricingContextFacade;
    }

    public List<ProductPriceDto> getProductPriceByVariantId(UUID tenantId, UUID variantId) {
        var productPriceResponseList = pricingContextFacade.getProductVariantById(tenantId, variantId);

        return productPriceResponseList.stream()
                .map(productPriceResponse -> {

                    var priceList = getProductPriceByProductId(tenantId, productPriceResponse.priceListId());

                    return new ProductPriceDto(
                            productPriceResponse.id(),
                            productPriceResponse.tenantId(),
                            productPriceResponse.productVariantId(),
                            priceList,
                            productPriceResponse.discountPercent(),
                            productPriceResponse.finalPrice(),
                            productPriceResponse.basePrice(),
                            productPriceResponse.minQuantity(),
                            productPriceResponse.validFrom(),
                            productPriceResponse.validTo()
                    );
                }).toList();
    }

    public PriceListDto getProductPriceByProductId(UUID tenantId, UUID productId) {
        var priceListResponse = pricingContextFacade.getPriceList(tenantId, productId);

        return new PriceListDto(
                priceListResponse.id(),
                priceListResponse.tenantId(),
                priceListResponse.code(),
                priceListResponse.name(),
                priceListResponse.description(),
                priceListResponse.currencyCode(),
                priceListResponse.validFrom(),
                priceListResponse.validTo(),
                priceListResponse.status()
        );
    }

    /**
     * Obtiene precios para múltiples variants en batch (optimizado).
     * Reduce queries DB al agrupar consultas de precios y price lists.
     * 
     * @param tenantId ID del tenant
     * @param variantIds Lista de IDs de variants
     * @return Mapa: variantId -> Lista de precios
     */
    public Map<UUID, List<ProductPriceDto>> getProductPricesByVariantIdsBatch(UUID tenantId, List<UUID> variantIds) {
        if (variantIds == null || variantIds.isEmpty()) {
            return new HashMap<>();
        }

        // Step 1: Obtener todas las respuestas de precios para cada variant
        Map<UUID, List<Object>> variantPriceResponses = new HashMap<>();
        Set<UUID> priceListIds = new HashSet<>();

        variantIds.forEach(variantId -> {
            var priceResponses = pricingContextFacade.getProductVariantById(tenantId, variantId);
            variantPriceResponses.put(variantId, priceResponses.stream().map(Object.class::cast).collect(Collectors.toList()));
            
            // Extraer price list IDs únicos
            priceResponses.forEach(priceResponse -> {
                priceListIds.add(priceResponse.priceListId());
            });
        });

        // Step 2: Obtener todos los price lists en batch
        Map<UUID, PriceListDto> priceListMap = new HashMap<>();
        priceListIds.forEach(priceListId -> {
            var priceListResponse = pricingContextFacade.getPriceList(tenantId, priceListId);
            var priceListDto = new PriceListDto(
                    priceListResponse.id(),
                    priceListResponse.tenantId(),
                    priceListResponse.code(),
                    priceListResponse.name(),
                    priceListResponse.description(),
                    priceListResponse.currencyCode(),
                    priceListResponse.validFrom(),
                    priceListResponse.validTo(),
                    priceListResponse.status()
            );
            priceListMap.put(priceListId, priceListDto);
        });

        // Step 3: Compilar resultado final
        Map<UUID, List<ProductPriceDto>> result = new HashMap<>();
        variantPriceResponses.forEach((variantId, priceResponses) -> {
            List<ProductPriceDto> prices = priceResponses.stream()
                    .map(priceResponse -> {
                        // Cast seguro a ProductPriceResponse
                        if (!(priceResponse instanceof Object)) {
                            return null;
                        }
                        
                        try {
                            var priceResp = (Object) priceResponse;
                            var priceListId = (UUID) priceResp.getClass().getMethod("priceListId").invoke(priceResp);
                            var priceList = priceListMap.getOrDefault(priceListId, 
                                new PriceListDto(null, null, null, null, null, null, null, null, null));

                            return new ProductPriceDto(
                                    (UUID) priceResp.getClass().getMethod("id").invoke(priceResp),
                                    (UUID) priceResp.getClass().getMethod("tenantId").invoke(priceResp),
                                    (UUID) priceResp.getClass().getMethod("productVariantId").invoke(priceResp),
                                    priceList,
                                    (java.math.BigDecimal) priceResp.getClass().getMethod("discountPercent").invoke(priceResp),
                                    (java.math.BigDecimal) priceResp.getClass().getMethod("finalPrice").invoke(priceResp),
                                    (java.math.BigDecimal) priceResp.getClass().getMethod("basePrice").invoke(priceResp),
                                    (Integer) priceResp.getClass().getMethod("minQuantity").invoke(priceResp),
                                    (java.time.Instant) priceResp.getClass().getMethod("validFrom").invoke(priceResp),
                                    (java.time.Instant) priceResp.getClass().getMethod("validTo").invoke(priceResp)
                            );
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            
            result.put(variantId, prices);
        });

        return result;
    }
}
