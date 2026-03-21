package codin.msbackendcore.iam.domain.services.userprofile;

import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.model.entities.UserProfile;
import codin.msbackendcore.ordering.domain.model.valueobjects.DocumentType;

import java.util.UUID;

public interface UserProfileDomainService {
    UserProfile createUserProfile(User user, UUID tenantId, String firstName, String lastName, String email, String phone, DocumentType documentType, String documentNumber);
    boolean existsByDocumentNumber(String documentNumber);
    UserProfile getUserProfileByUser(UUID userId);
}
