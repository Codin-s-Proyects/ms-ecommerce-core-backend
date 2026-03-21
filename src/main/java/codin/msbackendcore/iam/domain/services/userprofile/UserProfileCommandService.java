package codin.msbackendcore.iam.domain.services.userprofile;

import codin.msbackendcore.iam.domain.model.commands.CreateUserProfileCommand;
import codin.msbackendcore.iam.domain.model.entities.UserProfile;

import java.util.Optional;

public interface UserProfileCommandService {
    Optional<UserProfile> handle(CreateUserProfileCommand command);
}
