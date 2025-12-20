package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.commands.productvariant.CreateProductVariantBulkCommand;
import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;
import codin.msbackendcore.catalog.domain.services.productvariant.ProductVariantDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.ProductVariantRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

        if (productVariantRepository.existsByNameAndTenantIdAndProduct(name, tenantId, product))
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
    public ProductVariant updateProductVariant(UUID tenantId, UUID productVariantId, String name, Map<String, Object> attributes, Integer productQuantity) {

        var productVariant = productVariantRepository.findById(productVariantId)
                .orElseThrow(
                        () -> new NotFoundException("error.not_found", new String[]{productVariantId.toString()}, "productVariantId")
                );

        var product = productVariant.getProduct();

        if (!productVariant.getName().equals(name)
                && productVariantRepository.existsByNameAndTenantIdAndProduct(name, tenantId, product))
            throw new BadRequestException("error.already_exist", new String[]{name}, "name");

        try {
            String attributeJson = new ObjectMapper().writeValueAsString(attributes);
            String actualAttribute = new ObjectMapper().writeValueAsString(productVariant.getAttributes());

            if (!attributeJson.equals(actualAttribute) && productVariantRepository.existsByProductAndAttributes(tenantId, product.getId(), attributeJson))
                throw new BadRequestException("error.already_exist", new String[]{name}, "name");

        } catch (Exception e) {
            throw new BadRequestException("error.bad_request", new String[]{attributes.toString()}, "attributes");
        }

        String categoryName = product.getCategories() != null && !product.getCategories().isEmpty()
                ? product.getCategories().getFirst().getCategory().getName()
                : "";

        String brandName = product.getBrand() != null ? product.getBrand().getName() : null;

        productVariant.setSku(generateSku(name, categoryName, brandName, attributes, tenantId));
        productVariant.setName(name);
        productVariant.setAttributes(attributes);
        productVariant.setProductQuantity(productQuantity);

        return productVariantRepository.save(productVariant);
    }

    @Override
    @Transactional
    public List<ProductVariant> createProductVariantBulk(UUID tenantId, Product product, List<CreateProductVariantBulkCommand.VariantItemCommand> variants) {

        String categoryName = product.getCategories() != null && !product.getCategories().isEmpty()
                ? product.getCategories().getFirst().getCategory().getName()
                : "";

        String brandName = product.getBrand() != null ? product.getBrand().getName() : null;

        List<ProductVariant> existingVariants =
                productVariantRepository.findAllByProductAndTenantId(product, tenantId);


        ObjectMapper mapper = new ObjectMapper();

        Set<JsonNode> existingAttributesNodes = existingVariants.stream()
                .map(v -> {
                    Object attrs = v.getAttributes();
                    try {
                        if (attrs instanceof String) {
                            return mapper.readTree((String) attrs);
                        } else {
                            return mapper.valueToTree(attrs);
                        }
                    } catch (Exception e) {
                        return NullNode.getInstance();
                    }
                })
                .collect(Collectors.toSet());

        Set<String> requestNames = new HashSet<>();
        Set<JsonNode> requestAttributesNodes = new HashSet<>();

        List<ProductVariant> variantsToSave = new ArrayList<>();

        for (var item : variants) {

            if (!requestNames.add(item.name()))
                throw new BadRequestException("error.already_exist",
                        new String[]{item.name()}, "name");

            boolean nameExists = existingVariants.stream()
                    .anyMatch(v -> item.name().equals(v.getName()));
            if (nameExists)
                throw new BadRequestException("error.already_exist", new String[]{item.name()}, "name");

            JsonNode itemAttrsNode;
            try {
                Object raw = item.attributes();
                if (raw instanceof String) itemAttrsNode = mapper.readTree((String) raw);
                else itemAttrsNode = mapper.valueToTree(raw);
            } catch (Exception ex) {
                throw new BadRequestException("error.bad_request",
                        new String[]{item.attributes().toString()}, "attributes");
            }

            if (!requestAttributesNodes.add(itemAttrsNode))
                throw new BadRequestException("error.already_exist", new String[]{itemAttrsNode.toString()}, "attributes");


            if (existingAttributesNodes.contains(itemAttrsNode))
                throw new BadRequestException("error.already_exist", new String[]{itemAttrsNode.toString()}, "attributes");

            ProductVariant variant = ProductVariant.builder()
                    .tenantId(tenantId)
                    .product(product)
                    .sku(generateSku(
                            item.name(),
                            categoryName,
                            brandName,
                            item.attributes(),
                            tenantId
                    ))
                    .name(item.name())
                    .attributes(item.attributes())
                    .productQuantity(item.productQuantity())
                    .build();


            variantsToSave.add(variant);
        }

        return productVariantRepository.saveAll(variantsToSave);

    }

    @Override
    public List<ProductVariant> getVariantsByProductId(Product product, UUID tenantId) {
        return productVariantRepository.findAllByProductAndTenantId(product, tenantId);
    }

    @Override
    public ProductVariant getProductVariantById(UUID productVariantId) {
        return productVariantRepository.findById(productVariantId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{productVariantId.toString()}, "productVariantId")
                );
    }

    @Override
    public void deleteProductVariant(UUID tenantId, UUID productVariantId) {
        var productVariant = productVariantRepository.findProductVariantByTenantIdAndId(tenantId, productVariantId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{productVariantId.toString()}, "productVariantId")
                );

        productVariantRepository.delete(productVariant);
    }

    @Override
    public void reserve(UUID variantId, UUID tenantId, int qty) {
        ProductVariant variant = productVariantRepository.findByIdForUpdate(variantId, tenantId)
                .orElseThrow(
                        () -> new NotFoundException("error.not_found", new String[]{variantId.toString()}, "variantId")
                );

        variant.reserve(qty);

        productVariantRepository.save(variant);
    }

    @Override
    public void release(UUID variantId, UUID tenantId, int qty) {
        ProductVariant variant = productVariantRepository.findByIdForUpdate(variantId, tenantId)
                .orElseThrow(
                        () -> new NotFoundException("error.not_found", new String[]{variantId.toString()}, "variantId")
                );

        variant.release(qty);

        productVariantRepository.save(variant);
    }

    @Override
    public void confirm(UUID variantId, UUID tenantId, int qty) {
        ProductVariant variant = productVariantRepository.findByIdForUpdate(variantId, tenantId)
                .orElseThrow(
                        () -> new NotFoundException("error.not_found", new String[]{variantId.toString()}, "variantId")
                );

        variant.confirm(qty);

        productVariantRepository.save(variant);
    }
}
