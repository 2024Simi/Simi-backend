package com.project.simi.api.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TestEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public TestEntity() {
    }

    public TestEntity(String name) {
        this.name = name;
    }

    public static TestEntity createOf (String test) {
        return new TestEntity(test);
    }
}
