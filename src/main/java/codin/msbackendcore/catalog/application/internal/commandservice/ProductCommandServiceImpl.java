package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.entities.Product;
import codin.msbackendcore.catalog.domain.services.ProductCommandService;
import codin.msbackendcore.catalog.domain.services.ProductDomainService;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductDomainService productDomainService;

    public ProductCommandServiceImpl(ProductDomainService productDomainService) {
        this.productDomainService = productDomainService;
    }

    @Override
    public Product createProduct(Product product) {
        return productDomainService.createProduct(product);
    }
}
