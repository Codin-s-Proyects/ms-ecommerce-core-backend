package codin.msbackendcore.core.interfaces.rest.controller;

import codin.msbackendcore.core.domain.services.tenantbankaccount.TenantBankAccountCommandService;
import codin.msbackendcore.core.interfaces.dto.tenantbankaccount.CreateTenantBankAccountRequest;
import codin.msbackendcore.core.interfaces.dto.tenantbankaccount.TenantBankAccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/core/tenant-bank-accounts")
@Tag(name = "Tenant Bank Account", description = "API para la gestion de cuentas bancarias de tenants")
public class TenantBankAccountController {

    private final TenantBankAccountCommandService commandService;

    public TenantBankAccountController(TenantBankAccountCommandService commandService) {
        this.commandService = commandService;
    }


    @Operation(summary = "Creacion de un tenant")
    @ApiResponse(responseCode = "201", description = "Tenant creado correctamente")
    @PostMapping()
    public ResponseEntity<TenantBankAccountResponse> createTenantBankAccount(
            @Valid @RequestBody CreateTenantBankAccountRequest request
    ) {
        var command = request.toCommand();

        var tenantBankAccountCreated = commandService.handle(command);

        return ResponseEntity.status(201).body(
                new TenantBankAccountResponse(
                        tenantBankAccountCreated.getId(),
                        tenantBankAccountCreated.getTenant().getId(),
                        tenantBankAccountCreated.getBankName().name(),
                        tenantBankAccountCreated.getAccountType().name(),
                        tenantBankAccountCreated.getAccountHolder(),
                        tenantBankAccountCreated.getAccountLast4(),
                        tenantBankAccountCreated.getCurrencyCode(),
                        tenantBankAccountCreated.getStatus().name(),
                        tenantBankAccountCreated.isDefault(),
                        tenantBankAccountCreated.getCreatedAt(),
                        tenantBankAccountCreated.getUpdatedAt()
                )
        );
    }

}