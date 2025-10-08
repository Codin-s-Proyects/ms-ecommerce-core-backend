package codin.msbackendcore.iam.domain.services;

import codin.msbackendcore.iam.domain.model.commands.SignUpCommand;
import codin.msbackendcore.iam.domain.model.entities.User;

public interface UserDomainService {
    User registerNewUser(SignUpCommand command);
}
