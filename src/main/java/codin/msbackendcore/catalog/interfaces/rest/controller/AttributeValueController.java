package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.services.attributevalue.AttributeValueCommandService;
import codin.msbackendcore.catalog.interfaces.dto.attributevalue.AttributeValueCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.attributevalue.AttributeValueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/attribute-values")
@Tag(name = "Attribute Value Controller", description = "Operaciones relacionadas con los valores de los atributos de productos")
public class AttributeValueController {

    private final AttributeValueCommandService attributeValueCommandService;

    public AttributeValueController(AttributeValueCommandService attributeValueCommandService) {
        this.attributeValueCommandService = attributeValueCommandService;
    }

    @Operation(summary = "Crear un nuevo valor de atributo de producto")
    @PostMapping
    public ResponseEntity<AttributeValueResponse> createAttributeValue(@Valid @RequestBody AttributeValueCreateRequest req) {

        var command = req.toCommand();

        var saved = attributeValueCommandService.handle(command);

        var response = new AttributeValueResponse(
                saved.getId(),
                saved.getAttribute().getId(),
                saved.getValue(),
                saved.getLabel()
        );

        return ResponseEntity.status(201).body(response);
    }
}

