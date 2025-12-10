package codin.msbackendcore.core.interfaces.dto.tenant;

import codin.msbackendcore.core.domain.model.valueobjects.ContactInfo;
import codin.msbackendcore.core.domain.model.valueobjects.LegalInfo;
import codin.msbackendcore.core.domain.model.valueobjects.SocialInfo;
import codin.msbackendcore.core.domain.model.valueobjects.SupportInfo;
import codin.msbackendcore.core.interfaces.dto.mediaasset.MediaAssetResponse;
import codin.msbackendcore.core.interfaces.dto.tenantaddress.TenantAddressResponse;

import java.util.List;
import java.util.UUID;

public record TenantResponse(
        UUID id,
        String slug,
        String name,
        String plan,
        String status,
        String currencyCode,
        String locale,
        String complaintBookUrl,
        LegalInfo legal,
        ContactInfo contact,
        SupportInfo support,
        SocialInfo social,
        List<TenantAddressResponse> addresses,
        List<MediaAssetResponse> mediaAssets
) {
}