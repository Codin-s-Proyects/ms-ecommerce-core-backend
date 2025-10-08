package codin.msbackendcore.iam.domain.services;

import codin.msbackendcore.iam.domain.model.commands.SignInCommand;
import codin.msbackendcore.iam.domain.model.commands.SignUpCommand;
import codin.msbackendcore.iam.domain.model.entities.User;

import java.util.Optional;

public interface UserCommandService {
    Optional<User> handle(SignInCommand command);
    Optional<User> handle(SignUpCommand command);
}
