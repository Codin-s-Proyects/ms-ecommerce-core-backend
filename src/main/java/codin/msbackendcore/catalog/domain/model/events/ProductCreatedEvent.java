package codin.msbackendcore.catalog.domain.model.events;


import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;

import java.util.List;
import java.util.UUID;

public record ProductCreatedEvent(UUID tenantId, List<ProductVariant> variants) { }
