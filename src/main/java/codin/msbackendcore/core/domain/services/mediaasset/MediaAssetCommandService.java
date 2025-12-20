package codin.msbackendcore.core.domain.services.mediaasset;

import codin.msbackendcore.core.domain.model.commands.mediaasset.CreateMediaAssetCommand;
import codin.msbackendcore.core.domain.model.commands.mediaasset.DeleteMediaAssetCommand;
import codin.msbackendcore.core.domain.model.commands.mediaasset.UpdateMediaAssetCommand;
import codin.msbackendcore.core.domain.model.entities.MediaAsset;

public interface MediaAssetCommandService {
    MediaAsset handle(CreateMediaAssetCommand command);
    MediaAsset handle(UpdateMediaAssetCommand command);
    void handle(DeleteMediaAssetCommand command);

}
