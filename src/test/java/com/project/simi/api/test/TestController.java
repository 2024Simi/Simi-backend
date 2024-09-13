package com.project.simi.api.test;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.SuperIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TestController extends SuperIntegrationTest {

    @Autowired
    @Qualifier("testBean")
    private TestEntity testEntity;
    @Test
    public void test() throws Exception {
           mvc.perform(get("/api/v1/test"))
                .andExpect(status().isOk());
    }
}
