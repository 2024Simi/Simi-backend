package com.project.simi.domain.consent.domain;

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

import com.project.simi.domain.common.domain.AbstractJpaStringAssignedPersistable;
import com.project.simi.domain.consent.enums.ConsentType;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "simi_consent_version")
@AttributeOverride(name = "id", column = @Column(name = "consent_id"))
@Entity
@SQLRestriction("deleted_at is null")
public class ConsentVersion extends AbstractJpaStringAssignedPersistable {
    @Column(name = "consent_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ConsentType consentType;

    public static ConsentVersion of(String consentVersion, ConsentType consentType) {
        ConsentVersion consentVersion1 = new ConsentVersion();
        consentVersion1.id = consentVersion;
        consentVersion1.consentType = consentType;

        return consentVersion1;
    }
}
