package codin.msbackendcore.iam.application.internal.domainservice;

import codin.msbackendcore.iam.domain.model.entities.User;
import codin.msbackendcore.iam.domain.model.entities.UserProfile;
import codin.msbackendcore.iam.domain.services.userprofile.UserProfileDomainService;
import codin.msbackendcore.iam.infrastructure.persistence.jpa.UserProfileRepository;
import codin.msbackendcore.ordering.domain.model.valueobjects.DocumentType;
import codin.msbackendcore.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProfileDomainServiceImpl implements UserProfileDomainService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileDomainServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile createUserProfile(User user, UUID tenantId, String firstName, String lastName, String email, String phone, DocumentType documentType, String documentNumber) {
        var userProfile = UserProfile.builder()
                .user(user)
                .tenantId(tenantId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .documentType(documentType)
                .documentNumber(documentNumber)
                .build();

        return userProfileRepository.save(userProfile);
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return userProfileRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public UserProfile getUserProfileByUser(UUID userId) {
        return userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() ->
                        new NotFoundException("error.not_found", new String[]{userId.toString()}, "userId")
                );
    }
}
