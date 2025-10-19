package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalPricingService;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.model.events.ProductCreatedEvent;
import codin.msbackendcore.catalog.domain.services.ProductCommandService;
import codin.msbackendcore.catalog.domain.services.ProductDomainService;
import codin.msbackendcore.catalog.interfaces.dto.ProductCreateRequest;
import codin.msbackendcore.shared.domain.events.SimpleDomainEventPublisher;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductDomainService productDomainService;
    private final SimpleDomainEventPublisher eventPublisher;
    private final ExternalPricingService externalPricingService;

    public ProductCommandServiceImpl(ProductDomainService productDomainService, SimpleDomainEventPublisher eventPublisher, ExternalPricingService externalPricingService) {
        this.productDomainService = productDomainService;
        this.eventPublisher = eventPublisher;
        this.externalPricingService = externalPricingService;
    }

    @Transactional
    @Override
    public Product createProduct(ProductCreateRequest request) {
        Product p = new Product();
        p.setTenantId(request.tenantId());
        p.setName(request.name());
        p.setSlug(request.slug());
        p.setDescription(request.description());

        Map<String, ProductCreateRequest.VariantRequest> skuToRequest = request.variants() == null
                ? Map.of()
                : request.variants().stream()
                .collect(Collectors.toMap(ProductCreateRequest.VariantRequest::sku, vr -> vr));

        if (request.variants() != null) {
            var variants = request.variants().stream().map(vr -> {
                var pv = new ProductVariant();
                pv.setTenantId(request.tenantId());
                pv.setSku(vr.sku());
                pv.setName(vr.name());
                pv.setAttributes(vr.attributes());
                pv.setProduct(p);

                return pv;
            }).toList();
            p.getVariants().addAll(variants);
        }

        p.setHasVariants(!p.getVariants().isEmpty());

        Product saved = productDomainService.createProduct(p);

        List<UUID> variantIds = saved.getVariants().stream()
                .map(ProductVariant::getId)
                .toList();

        saved.getVariants().forEach(pv -> {
            var vr = skuToRequest.get(pv.getSku());
            if (vr != null) {
                externalPricingService.registerVariantPrices(
                        saved.getTenantId(),
                        pv.getId(),
                        vr.retailPrice(),
                        vr.wholesalePrice());
            }
        });

        eventPublisher.publish(new ProductCreatedEvent(saved.getTenantId(), variantIds));

        return saved;
    }
}
