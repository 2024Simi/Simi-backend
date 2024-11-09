package com.project.simi.mock.ai;

import com.project.simi.domain.ai.cerebras.api.ICerebrasRequestAPIClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockCerebrasConfig {
    @Bean
    @Primary
    public ICerebrasRequestAPIClient mockCerebrasRequestAPIClient() {
        return new MockCerebrasRequestRequestAPIClient();
    }
}
