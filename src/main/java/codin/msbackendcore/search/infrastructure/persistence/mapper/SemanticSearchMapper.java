package codin.msbackendcore.search.infrastructure.persistence.mapper;

import codin.msbackendcore.search.application.internal.dto.SemanticSearchDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SemanticSearchMapper {

    private final ObjectMapper objectMapper;

    public SemanticSearchMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public SemanticSearchDto toDto(String json) {
        try {
            return objectMapper.readValue(json, SemanticSearchDto.class);
        } catch (IOException e) {
            throw new IllegalStateException("Error mapping semantic search result", e);
        }
    }
}
