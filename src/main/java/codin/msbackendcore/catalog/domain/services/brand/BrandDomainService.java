package codin.msbackendcore.catalog.domain.services.brand;

import codin.msbackendcore.catalog.domain.model.entities.Brand;

import java.util.List;
import java.util.UUID;

public interface BrandDomainService {
    Brand createBrand(UUID tenantId, String name, String description, String logoUrl);
    List<Brand> getAllBrandsByTenantId(UUID tenantId);
    Brand getBrandById(UUID brandId);
}
