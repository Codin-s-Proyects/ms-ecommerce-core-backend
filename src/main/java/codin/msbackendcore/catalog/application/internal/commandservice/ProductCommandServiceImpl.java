package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.commands.product.CreateProductCommand;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.services.brand.BrandDomainService;
import codin.msbackendcore.catalog.domain.services.category.CategoryDomainService;
import codin.msbackendcore.catalog.domain.services.product.ProductCommandService;
import codin.msbackendcore.catalog.domain.services.product.ProductDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductDomainService productDomainService;
    private final CategoryDomainService categoryDomainService;
    private final BrandDomainService brandDomainService;

    public ProductCommandServiceImpl(ProductDomainService productDomainService, CategoryDomainService categoryDomainService, BrandDomainService brandDomainService) {
        this.productDomainService = productDomainService;
        this.categoryDomainService = categoryDomainService;
        this.brandDomainService = brandDomainService;
    }

    @Transactional
    @Override
    public Product handle(CreateProductCommand command) {

        var category = command.categoryId() != null ? categoryDomainService.getCategoryById(command.categoryId()) : null;
        var brand = command.brandId() != null ? brandDomainService.getBrandById(command.brandId()) : null;

        return productDomainService.createProduct(
                command.tenantId(),
                category,
                brand,
                command.name(),
                command.description(),
                command.meta()
        );

        // TODO: Registro de product - variants
        // TODO: Registro de product embeddings - asíncrono
        // TODO: Registro de precio de productos

//        Map<String, ProductCreateRequest.VariantRequest> skuToRequest = request.variants() == null
//                ? Map.of()
//                : request.variants().stream()
//                .collect(Collectors.toMap(ProductCreateRequest.VariantRequest::sku, vr -> vr));
//
//        if (request.variants() != null) {
//            var variants = request.variants().stream().map(vr -> {
//                var pv = new ProductVariant();
//                pv.setTenantId(request.tenantId());
//                pv.setSku(vr.sku());
//                pv.setName(vr.name());
//                pv.setAttributes(vr.attributes());
//                pv.setProduct(p);
//
//                return pv;
//            }).toList();
//            p.getVariants().addAll(variants);
//        }
//
//        p.setHasVariants(!p.getVariants().isEmpty());

//        List<UUID> variantIds = saved.getVariants().stream()
//                .map(ProductVariant::getId)
//                .toList();

//        saved.getVariants().forEach(pv -> {
//            var vr = skuToRequest.get(pv.getSku());
//            if (vr != null) {
//                externalPricingService.registerVariantPrices(
//                        saved.getTenantId(),
//                        pv.getId(),
//                        vr.retailPrice(),
//                        vr.wholesalePrice());
//            }
//        });
//
//        eventPublisher.publish(new ProductCreatedEvent(saved.getTenantId(), variantIds));

    }
}
