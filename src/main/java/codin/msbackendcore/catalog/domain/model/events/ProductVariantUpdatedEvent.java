package codin.msbackendcore.catalog.domain.model.events;


import codin.msbackendcore.catalog.domain.model.entities.ProductVariant;

public record ProductVariantUpdatedEvent(ProductVariant variant) { }
