package codin.msbackendcore.iam.infrastructure.token.jwt;

import codin.msbackendcore.iam.application.internal.outboundservices.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface BearerTokenService extends TokenService {
    String getBearerTokenFrom(HttpServletRequest token);
    String generateToken(Authentication authentication);
}
