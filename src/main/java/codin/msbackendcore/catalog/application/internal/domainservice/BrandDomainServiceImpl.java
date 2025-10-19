package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.services.BrandDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.BrandRepository;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BrandDomainServiceImpl implements BrandDomainService {

    private final BrandRepository brandRepository;

    public BrandDomainServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand getBrandById(UUID brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{brandId.toString()}, "brandId")
                );

    }
}
