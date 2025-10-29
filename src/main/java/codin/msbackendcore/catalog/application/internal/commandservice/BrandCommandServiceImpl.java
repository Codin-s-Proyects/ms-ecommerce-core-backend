package codin.msbackendcore.catalog.application.internal.commandservice;

import codin.msbackendcore.catalog.application.internal.outboundservices.ExternalCoreService;
import codin.msbackendcore.catalog.domain.model.commands.brand.CreateBrandCommand;
import codin.msbackendcore.catalog.domain.model.entities.Brand;
import codin.msbackendcore.catalog.domain.services.brand.BrandCommandService;
import codin.msbackendcore.catalog.domain.services.brand.BrandDomainService;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BrandCommandServiceImpl implements BrandCommandService {

    private final BrandDomainService brandDomainService;
    private final ExternalCoreService externalCoreService;

    public BrandCommandServiceImpl(BrandDomainService brandDomainService, ExternalCoreService externalCoreService) {
        this.brandDomainService = brandDomainService;
        this.externalCoreService = externalCoreService;
    }

    @Transactional
    @Override
    public Brand handle(CreateBrandCommand command) {

        if (!externalCoreService.existTenantById(command.tenantId())) {
            throw new BadRequestException("error.bad_request", new String[]{command.tenantId().toString()}, "tenantId");
        }

        return brandDomainService.createBrand(
                command.tenantId(),
                command.name(),
                command.description(),
                command.logoUrl()
        );
    }
}
