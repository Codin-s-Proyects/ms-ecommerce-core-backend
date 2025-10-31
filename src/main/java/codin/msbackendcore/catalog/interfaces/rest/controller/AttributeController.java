package codin.msbackendcore.catalog.interfaces.rest.controller;

import codin.msbackendcore.catalog.domain.model.queries.attribute.GetAllAttributeByTenantIdQuery;
import codin.msbackendcore.catalog.domain.services.attribute.AttributeCommandService;
import codin.msbackendcore.catalog.domain.services.attribute.AttributeQueryService;
import codin.msbackendcore.catalog.interfaces.dto.attribute.AttributeCreateRequest;
import codin.msbackendcore.catalog.interfaces.dto.attribute.AttributeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/attributes")
@Tag(name = "Attribute Controller", description = "Operaciones relacionadas con atributos de productos")
public class AttributeController {

    private final AttributeCommandService attributeCommandService;
    private final AttributeQueryService attributeQueryService;

    public AttributeController(AttributeCommandService attributeCommandService, AttributeQueryService attributeQueryService) {
        this.attributeCommandService = attributeCommandService;
        this.attributeQueryService = attributeQueryService;
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

    @Operation(summary = "Obtener todas los atributos por tenantId")
    @GetMapping("/tenant-id/{tenantId}")
    public ResponseEntity<List<AttributeResponse>> getAllAttributeByTenantId(@PathVariable UUID tenantId) {

        var query = new GetAllAttributeByTenantIdQuery(tenantId);

        var getList = attributeQueryService.handle(query);

        var responseList = getList.stream().map(attribute ->
                new AttributeResponse(
                        attribute.getId(),
                        attribute.getTenantId(),
                        attribute.getCode(),
                        attribute.getName(),
                        attribute.getDataType().name()
                )).toList();

        return ResponseEntity.status(200).body(responseList);
    }
}

