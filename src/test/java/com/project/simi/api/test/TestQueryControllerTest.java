package com.project.simi.api.test;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.simi.SuperIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TestQueryControllerTest extends SuperIntegrationTest {

    @Autowired
    @Qualifier("testBean")
    private TestEntity testEntity;
    @Test
    public void test() throws Exception {
           mvc.perform(get("/api/v1/test").header(ACCEPT, APPLICATION_JSON_VALUE)
                   .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                   .header(AUTHORIZATION, createDefaultAuthentication())
                   .param("name", "test")
                   .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }
}
