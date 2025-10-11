package codin.msbackendcore.iam.infrastructure.hashing.adapters;

import codin.msbackendcore.iam.infrastructure.hashing.bcrypt.HashingAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component("bcryptAdapter")
public class BCryptAdapter implements HashingAdapter {

    private final BCryptPasswordEncoder encoder;

    public BCryptAdapter() {
        this.encoder = new BCryptPasswordEncoder(); // podés ajustar strength
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