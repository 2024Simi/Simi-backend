package com.project.simi.domain.user.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

import com.project.simi.common.converter.StringToListConverter;
import com.project.simi.domain.auth.enums.AuthProviderEnum;
import com.project.simi.domain.auth.enums.AuthoriryEnum;
import com.project.simi.domain.common.domain.AbstractJpaIdentityPersistable;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "simi_user",
        indexes = {@jakarta.persistence.Index(name = "IDX_USER_LOGIN_ID", columnList = "login_id")})
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Entity
@SQLRestriction("deleted_at is null")
public class User extends AbstractJpaIdentityPersistable {

    @Comment("로그인 ID")
    @Column(name = "login_id", length = 50, nullable = false, unique = true)
    private String loginId;

    @Comment("비밀번호 해시")
    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Comment("프로필 이미지 URL")
    @Column(name = "profile_image_url", length = 255, nullable = false)
    @ColumnDefault("''")
    private String profileImageUrl = "";

    @Comment("실명")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Comment("닉네임")
    @Column(name = "nickname", length = 50, nullable = false)
    private String nickname;

    @Convert(converter = StringToListConverter.class)
    @Comment("권한")
    @Column(name = "authorities", length = 255, nullable = false)
    private List<AuthoriryEnum> authorities = new ArrayList<>();

    @Comment("소셜 로그인 제공자 또는 로컬 로그인")
    @Column(name = "provider", length = 20, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AuthProviderEnum provider;

    @Comment("계정 만료 여부")
    @Column(name = "non_expired", nullable = false)
    private boolean notExpired = true;

    @Comment("계정 정지 여부")
    @Column(name = "non_locked", nullable = false)
    private boolean nonLocked = true;

    public static User createOf(
            String loginId,
            String password,
            String profileImageUrl,
            String name,
            String nickname,
            List<AuthoriryEnum> authorities,
            AuthProviderEnum provider) {
        User user = new User();
        user.loginId = loginId;
        user.password = password;
        user.profileImageUrl = profileImageUrl;
        user.name = name;
        user.nickname = nickname;
        user.authorities = authorities;
        user.provider = provider;

        return user;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
