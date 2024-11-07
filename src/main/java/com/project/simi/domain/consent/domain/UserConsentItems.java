package com.project.simi.domain.consent.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.SQLRestriction;

import com.project.simi.domain.common.domain.AbstractJpaIdentityPersistable;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "consent_items")
@AttributeOverride(name = "id", column = @Column(name = "consent_item_id"))
@Entity
@SQLRestriction("deleted_at is null")
public class UserConsentItems extends AbstractJpaIdentityPersistable {

    @ManyToOne
    @JoinColumn(
            name = "terms_of_service_id",
            nullable = false,
            updatable = false,
            insertable = false)
    private TermsOfService termsOfService;

    @Column(name = "isAgreed", nullable = false)
    private Boolean isAgreed;

    @Column(name = "consent_version", nullable = false)
    private int consentVersion;
}
