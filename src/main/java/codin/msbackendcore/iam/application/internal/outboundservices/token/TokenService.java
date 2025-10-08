package codin.msbackendcore.iam.application.internal.outboundservices.token;

public interface TokenService {
    String generateToken(String username);
    String generateRefreshToken(String username);

    String getUsernameFromToken(String token);

    boolean validateToken(String token);
}
