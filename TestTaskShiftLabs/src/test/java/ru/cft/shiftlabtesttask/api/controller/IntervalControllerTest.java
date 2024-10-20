package ru.cft.shiftlabtesttask.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.shiftlabtesttask.ShiftlabTestTaskApplication;
import ru.cft.shiftlabtesttask.api.IntervalKind;
import ru.cft.shiftlabtesttask.core.service.ExecutorApiService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = ShiftlabTestTaskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntervalControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private ExecutorApiService executorApiService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mergeCharacterIntervalTest() throws Exception {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"c", "g"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"d", "h"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"q", "R"})));

        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/intervals/merge")
                .queryParam("kind","letters")
                .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testData));

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        verify(executorApiService).executeMergeInterval(testData, IntervalKind.letters);
    }

    @Test
    void getMinCharacterInterval() throws Exception {

        List<Character> minInterval = new ArrayList<>(List.of(new Character[]{'a', 'b'}));

        when(executorApiService.executeGetMin(IntervalKind.letters)).thenReturn(minInterval);

        mockMvc.perform(
            get("/api/v1/intervals/min")
                .queryParam("kind","letters")
        )
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(minInterval)));
        verify(executorApiService).executeGetMin(IntervalKind.letters);
    }

    @Test
    void mergeDigitIntervalTest() throws Exception {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{-3, 1})));
        testData.add(new ArrayList<>(List.of(new Object[]{2, 3})));
        testData.add(new ArrayList<>(List.of(new Object[]{2, 3})));
        testData.add(new ArrayList<>(List.of(new Object[]{4, 5})));
        testData.add(new ArrayList<>(List.of(new Object[]{10, 13})));

        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/intervals/merge")
            .queryParam("kind","digits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testData));

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        verify(executorApiService).executeMergeInterval(testData, IntervalKind.digits);
    }

    @Test
    void getMinDigitInterval() throws Exception {

        List<Integer> minInterval = new ArrayList<>(List.of(new Integer[]{1, 2}));

        when(executorApiService.executeGetMin(IntervalKind.digits)).thenReturn(minInterval);

        mockMvc.perform(
                get("/api/v1/intervals/min")
                    .queryParam("kind","digits")
            )
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(minInterval)));
        verify(executorApiService).executeGetMin(IntervalKind.digits);
    }
}