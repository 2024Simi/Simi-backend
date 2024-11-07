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

import com.project.simi.domain.common.domain.AbstractJpaIdentityPersistable;
import com.project.simi.domain.consent.enums.ConsentType;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "terms_of_service")
@AttributeOverride(name = "id", column = @Column(name = "terms_of_service_id"))
@Entity
@SQLRestriction("deleted_at is null")
public class TermsOfService extends AbstractJpaIdentityPersistable {

    @Column(name = "version", nullable = false)
    private int version;

    @Column(name = "consent_type", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ConsentType consentType;

    // TODO LIST API CACHING
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;
}
