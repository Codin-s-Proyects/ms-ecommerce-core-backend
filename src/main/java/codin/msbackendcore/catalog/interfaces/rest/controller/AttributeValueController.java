package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.model.queries.attributevalue.GetAllAttributeValueByAttributeQuery;
import codin.msbackendcore.catalog.domain.services.attributevalue.AttributeValueCommandService;
import codin.msbackendcore.catalog.domain.services.attributevalue.AttributeValueQueryService;
import codin.msbackendcore.catalog.interfaces.dto.attributevalue.AttributeValueCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.attributevalue.AttributeValueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/attribute-values")
@Tag(name = "Attribute Value Controller", description = "Operaciones relacionadas con los valores de los atributos de productos")
public class AttributeValueController {

    private final AttributeValueCommandService attributeValueCommandService;
    private final AttributeValueQueryService attributeValueQueryService;

    public AttributeValueController(AttributeValueCommandService attributeValueCommandService, AttributeValueQueryService attributeValueQueryService) {
        this.attributeValueCommandService = attributeValueCommandService;
        this.attributeValueQueryService = attributeValueQueryService;
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

    @Operation(summary = "Obtener todos los valores de atributo por atributo")
    @GetMapping("/attribute/{attributeId}")
    public ResponseEntity<List<AttributeValueResponse>> getAllAttributeValueByAttribute(@PathVariable UUID attributeId) {

        var query = new GetAllAttributeValueByAttributeQuery(attributeId);

        var getList = attributeValueQueryService.handle(query);

        var responseList = getList.stream().map(attributeValue ->
                new AttributeValueResponse(
                        attributeValue.getId(),
                        attributeValue.getAttribute().getId(),
                        attributeValue.getValue(),
                        attributeValue.getLabel()
                )).toList();

        return ResponseEntity.status(200).body(responseList);
    }
}

