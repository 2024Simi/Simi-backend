package com.project.simi.auth.domain;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;

import com.project.simi.user.domain.User;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(
        name = "refresh_token",
        indexes = {@Index(name = "IDX_REFRESH_TOKEN_VALUE", columnList = "refresh_token_value")})
@Entity
@SQLRestriction("expired_datetime > now()")
@DynamicUpdate
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id", columnDefinition = "bigint")
    private final Long id = 0L;

    @Comment("토큰 소유자 유저 아이디")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Comment("리프레시 토큰값")
    @Column(name = "refresh_token_value", length = 80, nullable = false)
    private String refreshTokenValue;

    @Comment("발급일시")
    @Column(name = "issued_datetime")
    private Instant issuedDatetime;

    @Comment("만료일시")
    @Column(name = "expired_datetime")
    private Instant expiredDatetime;

    public static RefreshToken createOf(
            User user,
            String refreshTokenValue,
            long tokenExpiryValue,
            ChronoUnit tokenExpiryUnit) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.user = user;
        refreshToken.refreshTokenValue = refreshTokenValue;
        refreshToken.issuedDatetime = Instant.now();
        refreshToken.expiredDatetime = Instant.now().plus(tokenExpiryValue, tokenExpiryUnit);

        return refreshToken;
    }

    public void expireRefreshToken() {
        this.expiredDatetime = Instant.now();
    }
}
