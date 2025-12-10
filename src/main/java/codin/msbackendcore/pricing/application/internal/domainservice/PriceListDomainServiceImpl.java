package codin.msbackendcore.pricing.application.internal.domainservice;

import codin.msbackendcore.pricing.domain.model.entities.PriceList;
import codin.msbackendcore.pricing.domain.services.pricelist.PriceListDomainService;
import codin.msbackendcore.pricing.infrastructure.jpa.PriceListRepository;
import codin.msbackendcore.shared.domain.exceptions.BadRequestException;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static codin.msbackendcore.shared.infrastructure.utils.CommonUtils.generatePriceListCode;

@Service
public class PriceListDomainServiceImpl implements PriceListDomainService {

    private final PriceListRepository priceListRepository;

    public PriceListDomainServiceImpl(PriceListRepository priceListRepository) {
        this.priceListRepository = priceListRepository;
    }

    @Transactional
    @Override
    public PriceList createPriceList(UUID tenantId, String name, String description, String currencyCode, Instant validFrom, Instant validTo) {

        if (priceListRepository.existsPriceListByTenantIdAndName(tenantId, name)) {
            throw new BadRequestException("error.already_exist", new String[]{name}, "name");
        }

        var priceList = PriceList.builder()
                .tenantId(tenantId)
                .code(generatePriceListCode(name, tenantId))
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

    @Override
    public List<PriceList> getPriceListsByTenantId(UUID tenantId) {
        return priceListRepository.findAllByTenantId(tenantId);
    }

    @Override
    public void deletePriceList(UUID tenantId, UUID priceListId) {
        var priceList = priceListRepository.findPriceListByTenantIdAndId(tenantId, priceListId)
                .orElseThrow(
                        () -> new NotFoundException("error.not_found", new String[]{priceListId.toString()}, "priceListId")
                );

        priceListRepository.delete(priceList);
    }
}
