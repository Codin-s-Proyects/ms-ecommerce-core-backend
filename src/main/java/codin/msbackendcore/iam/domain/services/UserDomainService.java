package codin.msbackendcore.iam.domain.services;

import codin.msbackendcore.iam.domain.model.commands.SignUpCommand;
import codin.msbackendcore.iam.domain.model.entities.Credential;
import codin.msbackendcore.iam.domain.model.entities.User;

import java.util.UUID;

public interface UserDomainService {
    User registerNewUser(SignUpCommand command, UUID systemUserId);
    boolean isPasswordValid(Credential credential, String rawPassword);
    void updateUserLastLogin(User user);
}
