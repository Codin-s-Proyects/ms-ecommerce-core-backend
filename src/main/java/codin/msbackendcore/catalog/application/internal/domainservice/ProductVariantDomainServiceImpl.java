package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductVariantRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateSku;

@Service
public class ProductVariantDomainServiceImpl implements ProductVariantDomainService {
    private final ProductVariantRepository productVariantRepository;

    public ProductVariantDomainServiceImpl(ProductVariantRepository productVariantRepository) {
        this.productVariantRepository = productVariantRepository;
    }

    @Transactional
    @Override
    public ProductVariant createProductVariant(UUID tenantId, Product product, String name, Map<String, Object> attributes, Integer productQuantity) {

        // TODO: Validar que los attributos y su valor ("color": "rojo"), sean validos

        if (productVariantRepository.existsByNameAndTenantId(name, tenantId))
            throw new BadRequestException("error.already_exist", new String[]{name}, "name");

        try {
            String attributeJson = new ObjectMapper().writeValueAsString(attributes);

            if (productVariantRepository.existsByProductAndAttributes(tenantId, product.getId(), attributeJson))
                throw new BadRequestException("error.already_exist", new String[]{name}, "name");

        } catch (Exception e) {
            throw new BadRequestException("error.bad_request", new String[]{attributes.toString()}, "attributes");
        }

        String categoryName = product.getCategories() != null && !product.getCategories().isEmpty()
                ? product.getCategories().getFirst().getCategory().getName()
                : "";

        String brandName = product.getBrand() != null ? product.getBrand().getName() : null;

        var productVariant = ProductVariant.builder()
                .tenantId(tenantId)
                .product(product)
                .sku(generateSku(name, categoryName, brandName, attributes, tenantId))
                .name(name)
                .attributes(attributes)
                .productQuantity(productQuantity)
                .build();

        return productVariantRepository.save(productVariant);
    }

    @Override
    public ProductVariant getProductVariantById(UUID productVariantId) {
        return productVariantRepository.findById(productVariantId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{productVariantId.toString()}, "productVariantId")
                );
    }
}
