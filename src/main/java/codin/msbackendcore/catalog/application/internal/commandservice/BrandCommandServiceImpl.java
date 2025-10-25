package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.domain.model.commands.brand.CreateBrandCommand;
import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.services.brand.BrandCommandService;
import codin.msbackendcore.catalog.domain.services.brand.BrandDomainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BrandCommandServiceImpl implements BrandCommandService {

    private final BrandDomainService brandDomainService;

    public BrandCommandServiceImpl(BrandDomainService brandDomainService) {
        this.brandDomainService = brandDomainService;
    }

    @Transactional
    @Override
    public Brand handle(CreateBrandCommand command) {

        return brandDomainService.createBrand(
                command.tenantId(),
                command.name(),
                command.description(),
                command.logoUrl()
        );
    }
}
