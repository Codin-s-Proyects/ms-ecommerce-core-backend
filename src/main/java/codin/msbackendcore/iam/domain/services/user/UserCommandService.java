package codin.msbackendcore.iam.domain.services.user;

import codin.msbackendcore.iam.application.internal.dto.SignInResult;
import codin.msbackendcore.iam.domain.model.commands.LogoutCommand;
import codin.msbackendcore.iam.domain.model.commands.RefreshTokenCommand;
import codin.msbackendcore.iam.domain.model.commands.SignInCommand;
import codin.msbackendcore.iam.domain.model.commands.SignUpCommand;
import codin.msbackendcore.iam.domain.model.entities.User;

import java.util.Optional;

public interface UserCommandService {
    Optional<SignInResult> handle(SignInCommand command);
    Optional<User> handle(SignUpCommand command);
    Optional<SignInResult> handle(RefreshTokenCommand command);
    Optional<Void> handle(LogoutCommand command);
}
