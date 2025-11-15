package codin.msbackendcore.core.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class LegalInfo {
    @Column(name = "legal_name", columnDefinition = "TEXT")
    private String legalName;

    @Column(name = "tax_id", columnDefinition = "TEXT")
    private String taxId;
}
