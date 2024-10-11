package com.project.simi.domain.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.simi.domain.ai.dto.ChatResponse;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LLMLoggingCommandService {
    public void saveLog(ChatResponse chatResponse) {
        log.info("Save log: {}", chatResponse);
        log.warn("NOT IMPLEMENTED: Save log");
    }

    public void saveErrorLog(Throwable throwable) {
        log.error(throwable.getMessage());
        log.warn("NOT IMPLEMENTED: Save error log");
    }
}
