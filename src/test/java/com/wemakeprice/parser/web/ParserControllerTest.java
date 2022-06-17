package com.wemakeprice.parser.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ParserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("파싱 페이지  조회 성공")
    @Test
    public void PARSER_FORM_VIEW_SUCCESS() throws Exception {
        mockMvc.perform(get("/v1/parser"))
                .andExpect(status().isOk())
                .andExpect(view().name("parser-form.html"));
    }

    @DisplayName("파싱 데이터 생성 성공")
    @Test
    public void PARSING_DATA_CREATE_SUCCESS() throws Exception {
        //given
        ParserReqDto req = new ParserReqDto();
        req.setType("text");
        req.setUnitNum(100);
        req.setUrl("http://055055.tistory.com");

        //when
        ResultActions result = mockMvc.perform(post("/v1/parser")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(req)));

        //then
        result
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.quotientData").exists())
                .andExpect(jsonPath("$.remainderData").exists())
                .andExpect(jsonPath("$.mergeData").exists())
                .andExpect(jsonPath("$.crawlingData").exists())
                .andExpect(jsonPath("$.quotientNum").exists())
                .andExpect(jsonPath("$.remainderNum").exists())
                .andExpect(jsonPath("$.mergeDataLength").exists());
    }
}
