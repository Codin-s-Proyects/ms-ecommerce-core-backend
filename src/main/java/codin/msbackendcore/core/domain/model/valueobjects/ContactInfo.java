package codin.msbackendcore.core.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ContactInfo {
    @Column(name = "contact_name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "contact_email", columnDefinition = "TEXT")
    private String email;

    @Column(name = "contact_phone", columnDefinition = "TEXT")
    private String phone;

    public ContactInfo() {
    }

    public ContactInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
