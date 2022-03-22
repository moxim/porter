package com.pdg.porter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {RestExporter.class})
class RestExporterTest {

    @Autowired
    private MockMvc mvc;

    @org.junit.jupiter.api.Test
    void answer() throws Exception {
        mvc.perform(get("/kuckuck")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Hi!"));
    }

    @org.junit.jupiter.api.Test
    void count() throws Exception {
        final String content = mvc.perform(get("/count")
                        .contentType(MediaType.TEXT_PLAIN))
                .andReturn()
                .getResponse()
                .getContentAsString();
        int testCount = Integer.parseInt(content);
        System.out.println(content);

        mvc.perform(get("/count")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).
                andExpect(content().string(Integer.toString(testCount)));

        testCount++;
        mvc.perform(get("/kuckuck"));
        mvc.perform(get("/count")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(Integer.toString(testCount)));

        testCount++;
        mvc.perform(get("/kuckuck"));
        mvc.perform(get("/last"));
        mvc.perform(get("/count")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(Integer.toString(testCount)));
    }

    @org.junit.jupiter.api.Test
    void last() throws Exception {
        Instant now = Instant.now();

        Thread.sleep(20);

        ResultActions resultActions = mvc.perform(get("/last"));
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString().replace('"', ' ').trim();
        Instant parsed = Instant.parse(contentAsString);

        assertTrue(parsed.isBefore(now));

        resultActions = mvc.perform(get("/last"));
        mvcResult = resultActions.andReturn();
        response = mvcResult.getResponse();
        contentAsString = response.getContentAsString().replace('"', ' ').trim();
        Instant newParsed = Instant.parse(contentAsString);

        assertEquals(parsed, newParsed);

        mvc.perform(get("/count"));
        resultActions = mvc.perform(get("/last"));
        mvcResult = resultActions.andReturn();
        response = mvcResult.getResponse();
        contentAsString = response.getContentAsString().replace('"', ' ').trim();
        newParsed = Instant.parse(contentAsString);

        assertEquals(parsed, newParsed);

        mvc.perform(get("/kuckuck"));
        resultActions = mvc.perform(get("/last"));
        mvcResult = resultActions.andReturn();
        response = mvcResult.getResponse();
        contentAsString = response.getContentAsString().replace('"', ' ').trim();
        newParsed = Instant.parse(contentAsString);

        assertNotEquals(parsed, newParsed);

        mvc.perform(get("/count"));
        resultActions = mvc.perform(get("/last"));
        mvcResult = resultActions.andReturn();
        response = mvcResult.getResponse();
        contentAsString = response.getContentAsString().replace('"', ' ').trim();
        Instant anotherNewParsed = Instant.parse(contentAsString);

        assertEquals(newParsed, anotherNewParsed);
    }
}
