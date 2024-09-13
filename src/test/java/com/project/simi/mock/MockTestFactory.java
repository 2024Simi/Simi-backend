package com.project.simi.mock;

import com.project.simi.api.test.TestEntity;
import com.project.simi.api.test.TestJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;

@TestComponent
public class MockTestFactory {
    @Autowired
    private TestJpaRepository testJpaRepository;
    public TestEntity createTest() {
        return testJpaRepository.save(
                TestEntity.createOf("test"));
    }

    @Bean
    @Qualifier("testBean")
    public TestEntity createTestBean() {
        return testJpaRepository.save(
                TestEntity.createOf("test"));
    }
}
