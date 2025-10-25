package codin.msbackendcore.pricing.application.internal.domainservice;

import codin.msbackendcore.pricing.domain.model.entities.Discount;
import codin.msbackendcore.pricing.domain.services.discount.DiscountDomainService;
import codin.msbackendcore.pricing.infrastructure.jpa.DiscountRepository;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class DiscountDomainServiceImpl implements DiscountDomainService {

    private final DiscountRepository discountRepository;

    public DiscountDomainServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Transactional
    @Override
    public Discount createDiscount(UUID tenantId, String name, String description, BigDecimal percentage, Instant startsAt, Instant endsAt) {

        var discount = Discount.builder()
                .tenantId(tenantId)
                .name(name)
                .description(description)
                .percentage(percentage)
                .startsAt(startsAt)
                .endsAt(endsAt)
                .build();

        return discountRepository.save(discount);
    }

    @Override
    public Discount getDiscountByTenantAndId(UUID tenantId, UUID discountId) {
        return discountRepository.findByTenantIdAndId(tenantId, discountId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{discountId.toString()}, "discountId")
                );
    }
}
