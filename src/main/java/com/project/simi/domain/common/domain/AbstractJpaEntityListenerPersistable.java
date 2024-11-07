package com.project.simi.domain.common.domain;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.project.simi.domain.user.domain.User;

@Getter
@MappedSuperclass
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractJpaEntityListenerPersistable {
    @Comment("")
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    protected Long createdBy = 0L;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false, insertable = false)
    protected User createdByUser;

    @Comment("생성일시")
    @CreatedDate
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE")
    protected Instant createdAt;

    @Comment("")
    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    protected Long updatedBy = 0L;

    @Comment("수정일시")
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    protected Instant updatedAt;

    @Comment("")
    @Column(name = "deleted_by", nullable = true)
    protected Long deletedBy;

    @Comment("삭제일시")
    @Column(name = "deleted_at", nullable = true, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    protected Instant deletedAt;

    public void delete() {
        this.delete(0L);
    }

    public void delete(Long deletedBy) {
        this.deletedBy = deletedBy;
        this.deletedAt = Instant.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
