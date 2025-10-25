package codin.msbackendcore.catalog.application.internal.domainservice;

import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.services.brand.BrandDomainService;
import codin.msbackendcore.catalog.infrastructure.persistence.jpa.BrandRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generateSlug;

@Service
public class BrandDomainServiceImpl implements BrandDomainService {

    private final BrandRepository brandRepository;

    public BrandDomainServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand createBrand(UUID tenantId, String name, String description, String logoUrl) {
        if (brandRepository.existsByTenantIdAndName(tenantId, name))
            throw new BadRequestException("error.already_exist", new String[]{name}, "name");

        var brand = Brand.builder()
                .tenantId(tenantId)
                .name(name)
                .slug(generateSlug(name))
                .description(description)
                .logoUrl(logoUrl)
                .build();

        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> getAllBrandsByTenantId(UUID tenantId) {
        return brandRepository.findAllByTenantId(tenantId);
    }

    @Override
    public Brand getBrandById(UUID brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{brandId.toString()}, "brandId")
                );

    }
}
