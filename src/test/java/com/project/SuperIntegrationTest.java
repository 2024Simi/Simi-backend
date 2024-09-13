package com.project;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.simi.mock.MockTestFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@Import({
        MockTestFactory.class
})
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@ComponentScan(basePackages = "com.project.simi")
public class SuperIntegrationTest {
    @Autowired
    private WebApplicationContext context;

/*
    @Autowired
    protected JwtTokenFactory jwtTokenFactory;
*/

    protected MockMvc mvc;
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected MockTestFactory mockTestFactory;


    @BeforeEach
    protected void setup(RestDocumentationContextProvider restDocumentation) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ssXXX"));

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
//                .apply(springSecurity())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .alwaysDo(print())
                .build();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mockTestFactory.createTest();

    }

//    protected String createDefaultAuthentication() {
//        return jwtTokenFactory.createDefaultAuthentication();
//    }

    protected FieldDescriptor[] commonResponseFields(FieldDescriptor... dataFields) {
        return buildResponseFields("data", dataFields);
    }

    protected FieldDescriptor[] commonListResponseFields(FieldDescriptor... dataFields) {
        return buildResponseFields("data[]", dataFields);
    }

    private FieldDescriptor[] buildResponseFields(String dataPrefix, FieldDescriptor... dataFields) {
        List<FieldDescriptor> list = new ArrayList<>();

        list.add(fieldWithPath("code").type(STRING).description("response code"));
        list.add(fieldWithPath("message").type(STRING).description("response message"));
        list.add(fieldWithPath(dataPrefix).type(dataPrefix.endsWith("[]") ? ARRAY : "OBJECT").description("response data"));

        for (FieldDescriptor dataField : dataFields) {
            list.add(fieldWithPath(dataPrefix + "." + dataField.getPath())
                    .type(dataField.getType())
                    .description(dataField.getDescription()));
        }

        return list.toArray(new FieldDescriptor[0]);
    }




    protected FieldDescriptor[] pageResponseFields(FieldDescriptor... dataFields) {
        FieldDescriptor[] contents = {fieldWithPath("content[]").type(ARRAY).description("content").optional()};
        FieldDescriptor[] pageDescriptor = {
                fieldWithPath("pageable.sort.empty").type(BOOLEAN).description("비었는지 여부"),
                fieldWithPath("pageable.sort.sorted").type(BOOLEAN).description("정렬됐는지 여부"),
                fieldWithPath("pageable.sort.unsorted").type(BOOLEAN).description("정렬 안됐는지 여부"),

                fieldWithPath("pageable.offset").type(NUMBER).description("몇번째 데이터인지(0부터시작)"),
                fieldWithPath("pageable.pageSize").type(NUMBER).description("한 페이지당 조회할 데이더 갯수"),
                fieldWithPath("pageable.pageNumber").type(NUMBER).description("현재 페이지번호"),
                fieldWithPath("pageable.unpaged").type(BOOLEAN).description("페이징 정보를 안 포함하는지 여부"),
                fieldWithPath("pageable.paged").type(BOOLEAN).description("페이징 정보를 포함하는지 여부"),

                fieldWithPath("totalElements").type(NUMBER).description("테이블 총 데이터 개수"),
                fieldWithPath("totalPages").type(NUMBER).description("전체 페이지 개수"),
                fieldWithPath("last").type(BOOLEAN).description("마지막 페이지인지 여부"),
                fieldWithPath("size").type(NUMBER).description("한 페이지당 조회할 데이터 개수"),
                fieldWithPath("number").type(NUMBER).description("현재 페이지 번호"),

                fieldWithPath("sort.empty").type(BOOLEAN).description("비었는지 여부"),
                fieldWithPath("sort.sorted").type(BOOLEAN).description("정렬됐는지 여부"),
                fieldWithPath("sort.unsorted").type(BOOLEAN).description("정렬 안됐는지 여부"),

                fieldWithPath("first").type(BOOLEAN).description("첫페이진지 여부"),
                fieldWithPath("numberOfElements").type(NUMBER).description("총 데이터 수"),
                fieldWithPath("empty").type(BOOLEAN).description("비었는지 여부"),
        };

        FieldDescriptor[] fields = Arrays.stream(dataFields)
                .map(dataField -> fieldWithPath("content[]." + dataField.getPath())
                        .type(dataField.getType())
                        .description(dataField.getDescription()))
                .toArray(FieldDescriptor[]::new);

        return Stream.concat(
                        Stream.concat(Arrays.stream(contents), Arrays.stream(fields)),
                        Arrays.stream(pageDescriptor)
                )
                .toArray(FieldDescriptor[]::new);
    }

    protected FieldDescriptor[] sliceResponseFields(FieldDescriptor... dataFields) {
        FieldDescriptor[] contents = {fieldWithPath("content[]").type(ARRAY).description("content").optional()};
        FieldDescriptor[] pageDescriptor = {
                fieldWithPath("pageable.sort.empty").type(BOOLEAN).description("비었는지 여부"),
                fieldWithPath("pageable.sort.sorted").type(BOOLEAN).description("정렬됐는지 여부"),
                fieldWithPath("pageable.sort.unsorted").type(BOOLEAN).description("정렬 안됐는지 여부"),

                fieldWithPath("pageable.offset").type(NUMBER).description("몇번째 데이터인지(0부터시작)"),
                fieldWithPath("pageable.pageSize").type(NUMBER).description("한 페이지당 조회할 데이터 갯수"),
                fieldWithPath("pageable.pageNumber").type(NUMBER).description("현재 페이지번호"),
                fieldWithPath("pageable.unpaged").type(BOOLEAN).description("페이징 정보를 안 포함하는지 여부"),
                fieldWithPath("pageable.paged").type(BOOLEAN).description("페이징 정보를 포함하는지 여부"),

                fieldWithPath("sort.empty").type(BOOLEAN).description("비었는지 여부"),
                fieldWithPath("sort.sorted").type(BOOLEAN).description("정렬됐는지 여부"),
                fieldWithPath("sort.unsorted").type(BOOLEAN).description("정렬 안됐는지 여부"),

                fieldWithPath("first").type(BOOLEAN).description("첫페이지인지 여부"),
                fieldWithPath("last").type(BOOLEAN).description("마지막 페이지인지 여부"),
                fieldWithPath("size").type(NUMBER).description("한 페이지당 조회할 데이터 개수"),
                fieldWithPath("number").type(NUMBER).description("현재 페이지 번호"),
                fieldWithPath("numberOfElements").type(NUMBER).description("총 데이터 수"),
                fieldWithPath("empty").type(BOOLEAN).description("비었는지 여부"),
        };

        FieldDescriptor[] fields = Arrays.stream(dataFields)
                .map(dataField -> fieldWithPath("content[]." + dataField.getPath())
                        .type(dataField.getType())
                        .description(dataField.getDescription()))
                .toArray(FieldDescriptor[]::new);

        return Stream.concat(
                        Stream.concat(Arrays.stream(contents), Arrays.stream(fields)),
                        Arrays.stream(pageDescriptor)
                )
                .toArray(FieldDescriptor[]::new);
    }
}
