package codin.msbackendcore.iam.infrastructure.hashing.bcrypt;


import codin.msbackendcore.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface Argon2idHashingService extends HashingService, PasswordEncoder {
}
