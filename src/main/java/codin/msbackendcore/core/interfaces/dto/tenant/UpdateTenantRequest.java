package codin.msbackendcore.core.interfaces.dto.tenant;

import codin.msbackendcore.core.domain.model.commands.tenant.UpdateTenantCommand;
import codin.msbackendcore.core.domain.model.entities.TenantAddress;
import codin.msbackendcore.core.domain.model.valueobjects.ContactInfo;
import codin.msbackendcore.core.domain.model.valueobjects.LegalInfo;
import codin.msbackendcore.core.domain.model.valueobjects.SocialInfo;
import codin.msbackendcore.core.domain.model.valueobjects.SupportInfo;
import codin.msbackendcore.core.interfaces.dto.tenantaddress.CreateTenantAddressRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UpdateTenantRequest(
        @NotBlank String name,
        @NotBlank String legalName,
        @NotBlank String taxId,
        @NotBlank String contactName,
        @NotBlank String contactEmail,
        @NotBlank String contactPhone,
        @NotBlank String supportEmail,
        @NotBlank String supportPhone,
        @NotBlank String whatsapp,
        @NotBlank String facebook,
        @NotBlank String instagram,
        @NotBlank String twitter,
        @NotBlank String currencyCode,
        @NotBlank String locale,
        @NotNull List<CreateTenantAddressRequest> addresses
) {
    public UpdateTenantCommand toCommand(UUID tenantId) {

        var addressCommands = addresses.stream()
                .map(address -> new TenantAddress(
                        address.line1(),
                        address.city(),
                        address.country()
                ))
                .toList();

        return new UpdateTenantCommand(
                tenantId,
                name,
                currencyCode,
                locale,
                new LegalInfo(legalName, taxId),
                new ContactInfo(contactName, contactEmail, contactPhone),
                new SupportInfo(supportEmail, supportPhone),
                new SocialInfo(whatsapp, facebook, instagram, twitter),
                addressCommands
        );
    }
}
