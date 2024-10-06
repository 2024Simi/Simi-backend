package com.project.simi.domain.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.simi.domain.diary.domain.Diary;

public interface DiaryJpaRepository extends JpaRepository<Diary, Long> {}
