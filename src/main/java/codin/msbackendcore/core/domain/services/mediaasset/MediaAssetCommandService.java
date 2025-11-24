package codin.msbackendcore.core.domain.services.mediaasset;

import codin.msbackendcore.catalog.domain.model.commands.category.DeleteCategoryCommand;
import codin.msbackendcore.core.domain.model.commands.mediaasset.CreateMediaAssetCommand;
import codin.msbackendcore.core.domain.model.commands.mediaasset.DeleteMediaAssetCommand;
import codin.msbackendcore.core.domain.model.entities.MediaAsset;

public interface MediaAssetCommandService {
    MediaAsset handle(CreateMediaAssetCommand command);
    void handle(DeleteMediaAssetCommand command);

}
