package codin.msbackendcore.core.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class SocialInfo {

    @Column(name = "whatsapp", columnDefinition = "TEXT")
    private String whatsapp;

    @Column(name = "facebook", columnDefinition = "TEXT")
    private String facebook;

    @Column(name = "instagram", columnDefinition = "TEXT")
    private String instagram;

    @Column(name = "twitter", columnDefinition = "TEXT")
    private String twitter;
}

