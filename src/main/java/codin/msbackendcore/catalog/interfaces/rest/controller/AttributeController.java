package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.services.attribute.AttributeCommandService;
import codin.msbackendcore.catalog.interfaces.dto.attribute.AttributeCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.attribute.AttributeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/attributes")
@Tag(name = "Attribute Controller", description = "Operaciones relacionadas con atributos de productos")
public class AttributeController {

    private final AttributeCommandService attributeCommandService;

    public AttributeController(AttributeCommandService attributeCommandService) {
        this.attributeCommandService = attributeCommandService;
    }

    @Operation(summary = "Crear un nuevo atributo de producto")
    @PostMapping
    public ResponseEntity<AttributeResponse> createAttribute(@Valid @RequestBody AttributeCreateRequest req) {

        var command = req.toCommand();

        var saved = attributeCommandService.handle(command);

        var response = new AttributeResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getCode(),
                saved.getName(),
                saved.getDataType().name()
        );

        return ResponseEntity.status(201).body(response);
    }
}

