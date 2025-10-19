package codin.msbackendcore.catalog.domain.services;

import codin.msbackendcore.catalog.domain.model.entities.Brand;

import java.util.UUID;

public interface BrandDomainService {
    Brand getBrandById(UUID brandId);
}
