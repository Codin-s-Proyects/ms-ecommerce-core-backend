package codin.msbackendcore.core.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class SupportInfo {
    @Column(name = "support_email", columnDefinition = "TEXT")
    private String email;

    @Column(name = "support_phone", columnDefinition = "TEXT")
    private String phone;

    public SupportInfo() {
    }

    public SupportInfo(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }
}
