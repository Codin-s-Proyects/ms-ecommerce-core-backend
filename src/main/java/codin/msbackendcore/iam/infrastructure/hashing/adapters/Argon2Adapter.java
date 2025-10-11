package codin.msbackendcore.iam.infrastructure.hashing.adapters;

import codin.msbackendcore.iam.infrastructure.hashing.bcrypt.HashingAdapter;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("argon2Adapter")
@Primary
public class Argon2Adapter implements HashingAdapter {

    private final Argon2PasswordEncoder encoder;

    public Argon2Adapter() {
        this.encoder = new Argon2PasswordEncoder(16, 32, 1, 1 << 12, 3);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
