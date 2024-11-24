package com.project.simi.domain.user.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Comment;

import com.project.simi.domain.common.domain.AbstractJpaLongAssignedPersistable;
import com.project.simi.domain.user.dto.UserConsentDto.Request;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "simi_user_consent")
public class UserConsent extends AbstractJpaLongAssignedPersistable {

    @MapsId private Long userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @Comment("사용자 ID")
    private User user;

    @Comment("개인정보 처리방침 동의 여부")
    @Column(name = "is_agree_private_policy", nullable = false)
    private Boolean isAgreePrivatePolicy;

    @Comment("개인정보 처리방침 동의 일시")
    @Column(name = "agree_private_policy_at")
    private LocalDateTime agreePrivatePolicyAt;

    @Comment("서비스 이용약관 동의 여부")
    @Column(name = "is_agree_terms_of_service", nullable = false)
    private Boolean isAgreeTermsOfService;

    @Comment("서비스 이용약관 동의 일시")
    private LocalDateTime agreeTermsOfServiceAt;

    @Comment("마케팅 정보 수신 동의 여부")
    @Column(name = "is_agree_marketing", nullable = false)
    private Boolean isAgreeMarketing;

    @Comment("마케팅 정보 수신 동의 일시")
    @Column(name = "agree_marketing_at")
    private LocalDateTime agreeMarketingAt;

    public static UserConsent createOf(User user) {
        return new UserConsent(user.getId(), user, false, null, false, null, false, null);
    }

    public void agreePrivatePolicy() {
        this.isAgreePrivatePolicy = true;
        this.agreePrivatePolicyAt = LocalDateTime.now();
    }

    public void agreeTermsOfService() {
        this.isAgreeTermsOfService = true;
        this.agreeTermsOfServiceAt = LocalDateTime.now();
    }

    public void agreeMarketing() {
        this.isAgreeMarketing = true;
        this.agreeMarketingAt = LocalDateTime.now();
    }

    public void update(Request request) {
        if (request.isAgreePrivatePolicy() && !isAgreePrivatePolicy) {
            agreePrivatePolicy();
        }
        if (request.isAgreeTermsOfService() && !isAgreeTermsOfService) {
            agreeTermsOfService();
        }
        if (request.isAgreeMarketing() && !isAgreeMarketing) {
            agreeMarketing();
        }
    }
}
