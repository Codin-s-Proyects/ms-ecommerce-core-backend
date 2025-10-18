package codin.msbackendcore.shared.domain.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publicador genérico para eventos de dominio.
 * Permite publicar eventos de forma desacoplada en la aplicación.
 */
@Component
public class SimpleDomainEventPublisher {

    private final ApplicationEventPublisher springPublisher;

    public SimpleDomainEventPublisher(ApplicationEventPublisher springPublisher) {
        this.springPublisher = springPublisher;
    }

    public void publish(Object event) {
        springPublisher.publishEvent(event);
    }
}