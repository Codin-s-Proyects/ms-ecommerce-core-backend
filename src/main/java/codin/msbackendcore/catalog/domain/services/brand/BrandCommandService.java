package codin.msbackendcore.catalog.domain.services.brand;

import codin.msbackendcore.catalog.domain.model.commands.brand.CreateBrandCommand;
import codin.msbackendcore.catalog.domain.model.entities.Brand;

public interface BrandCommandService {
    Brand handle(CreateBrandCommand command);
}
