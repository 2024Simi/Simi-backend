package com.project.simi.domain.consent.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import org.hibernate.annotations.SQLRestriction;

import com.project.simi.domain.common.domain.AbstractJpaIdentityPersistable;
import com.project.simi.domain.consent.enums.ConsentType;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "simi_user_consent")
@AttributeOverride(name = "id", column = @Column(name = "user_consent_id"))
@Entity
@SQLRestriction("deleted_at is null")
public class UserConsent extends AbstractJpaIdentityPersistable {

    @Column(name = "consent_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ConsentType consentType;

    @Column(name = "consent_version", nullable = false, length = 20)
    private String consentVersion;

    @Column(name = "consent_agreed_at", nullable = false)
    private LocalDateTime consentAgreedAt;

    public static UserConsent of(ConsentType consentType, String consentVersion) {
        UserConsent userConsent = new UserConsent();
        userConsent.consentType = consentType;
        userConsent.consentVersion = consentVersion;
        userConsent.consentAgreedAt = LocalDateTime.now();

        return userConsent;
    }
}
