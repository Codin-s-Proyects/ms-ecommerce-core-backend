package codin.msbackendcore.iam.application.internal.queryservice;

import codin.msbackendcore.iam.domain.model.entities.UserProfile;
import codin.msbackendcore.iam.domain.model.queries.GetUserProfileByUserQuery;
import codin.msbackendcore.iam.domain.services.userprofile.UserProfileDomainService;
import codin.msbackendcore.iam.domain.services.userprofile.UserProfileQueryService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileQueryServiceImpl implements UserProfileQueryService {

    private final UserProfileDomainService userProfileDomainService;

    public UserProfileQueryServiceImpl(UserProfileDomainService userProfileDomainService) {
        this.userProfileDomainService = userProfileDomainService;
    }

    @Override
    public UserProfile handle(GetUserProfileByUserQuery query) {
        return userProfileDomainService.getUserProfileByUser(query.userId());
    }
}
