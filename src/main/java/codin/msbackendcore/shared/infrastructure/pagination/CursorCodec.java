package codin.msbackendcore.shared.infrastructure.pagination;

import codin.msbackendcore.shared.infrastructure.pagination.model.CursorToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class CursorCodec {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private CursorCodec() {
    }

    public static String encode(CursorToken token) {
        try {
            String json = MAPPER.writeValueAsString(token);
            return Base64.getUrlEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to encode cursor", e);
        }
    }

    public static CursorToken decode(String encoded) {
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(encoded);
            String json = new String(decoded, StandardCharsets.UTF_8);
            return MAPPER.readValue(json, CursorToken.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid or corrupted cursor token", e);
        }
    }
}
