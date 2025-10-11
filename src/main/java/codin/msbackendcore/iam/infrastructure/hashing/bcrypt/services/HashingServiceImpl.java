package codin.msbackendcore.iam.infrastructure.hashing.bcrypt.services;

import codin.msbackendcore.iam.infrastructure.hashing.bcrypt.Argon2idHashingService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashingServiceImpl implements Argon2idHashingService {

    private final Argon2PasswordEncoder passwordEncoder;

    public HashingServiceImpl() {
        this.passwordEncoder = new Argon2PasswordEncoder(
                16,
                32,
                1,
                2,
                4
        );
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
