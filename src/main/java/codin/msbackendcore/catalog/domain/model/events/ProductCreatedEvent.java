package codin.msbackendcore.catalog.domain.model.events;


import java.util.List;
import java.util.UUID;

/**
 * Evento de dominio que se dispara al crear un producto.
 * Contiene el tenantId y los IDs de las variantes creadas.
 */
public record ProductCreatedEvent(UUID tenantId, List<UUID> variantIds) { }
