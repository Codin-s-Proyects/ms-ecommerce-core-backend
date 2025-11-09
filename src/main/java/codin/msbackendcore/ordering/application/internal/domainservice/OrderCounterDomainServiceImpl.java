package codin.msbackendcore.ordering.application.internal.domainservice;

import codin.msbackendcore.ordering.domain.model.entities.OrderCounter;
import codin.msbackendcore.ordering.domain.services.ordercounter.OrderCounterDomainService;
import codin.msbackendcore.ordering.infrastructure.persistence.jpa.OrderCounterRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.UUID;

@Service
public class OrderCounterDomainServiceImpl implements OrderCounterDomainService {

    private final OrderCounterRepository orderCounterRepository;

    public OrderCounterDomainServiceImpl(OrderCounterRepository orderCounterRepository) {
        this.orderCounterRepository = orderCounterRepository;
    }

    @Override
    public int getOrderCounterByTenant(UUID tenantId) {
        int currentYear = Year.now().getValue();

        OrderCounter counter = orderCounterRepository.findByTenantId(tenantId)
                .orElseGet(() -> {
                    OrderCounter newCounter = OrderCounter.builder()
                            .tenantId(tenantId)
                            .currentYear(currentYear)
                            .lastNumber(0)
                            .build();

                    return orderCounterRepository.save(newCounter);
                });

        if (counter.getCurrentYear() != currentYear) {
            counter.setCurrentYear(currentYear);
            counter.setLastNumber(0);
        }

        int nextNumber = counter.getLastNumber() + 1;
        counter.setLastNumber(nextNumber);
        orderCounterRepository.save(counter);

        return nextNumber;
    }
}
