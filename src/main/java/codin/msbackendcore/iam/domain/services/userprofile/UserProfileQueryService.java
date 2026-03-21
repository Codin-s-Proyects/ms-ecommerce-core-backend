package codin.msbackendcore.iam.domain.services.userprofile;

import codin.msbackendcore.iam.domain.model.entities.UserProfile;
import codin.msbackendcore.iam.domain.model.queries.GetUserProfileByUserQuery;

public interface UserProfileQueryService {
    UserProfile handle(GetUserProfileByUserQuery query);
}
