package codin.msbackendcore.pricing.application.internal.domainservice;

import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.services.pricinglist.PriceListDomainService;
import codin.msbackendcore.pricing.infrastructure.jpa.PriceListRepository;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PriceListDomainServiceImpl implements PriceListDomainService {

    private final PriceListRepository priceListRepository;

    public PriceListDomainServiceImpl(PriceListRepository priceListRepository) {
        this.priceListRepository = priceListRepository;
    }

    @Transactional
    @Override
    public PriceList createPriceList(UUID tenantId, String code, String name, String description, String currencyCode, Instant validFrom, Instant validTo) {

        var priceList = PriceList.builder()
                .tenantId(tenantId)
                .code(code)
                .name(name)
                .description(description)
                .currencyCode(currencyCode)
                .validFrom(validFrom)
                .validTo(validTo)
                .build();

        return priceListRepository.save(priceList);
    }

    @Override
    public PriceList getPriceListByTenantAndId(UUID tenantId, UUID priceListId) {
        return priceListRepository.findPriceListByTenantIdAndId(tenantId, priceListId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{priceListId.toString()}, "priceListId")
                );
    }
}
