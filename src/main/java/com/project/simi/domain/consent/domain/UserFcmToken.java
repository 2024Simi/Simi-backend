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
import com.project.simi.domain.consent.enums.NotificationStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_fcm_token")
@AttributeOverride(name = "id", column = @Column(name = "user_fcm_token_id"))
@Entity
@SQLRestriction("deleted_at is null")
public class UserFcmToken extends AbstractJpaIdentityPersistable {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "device_id", nullable = false, length = 100)
    private String deviceId;

    @Column(name = "fcm_token", nullable = false)
    private String fcmToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private NotificationStatus status; // FCM 토큰 상태 (활성, 비활성 등)
}
