package ru.cft.shiftlabtesttask.core.mapper;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cft.shiftlabtesttask.core.entities.DigitInterval;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DigitIntervalMapperTest {
    @InjectMocks
    DigitIntervalMapper digitIntervalMapper;

    @Test
    void mapFromObjectListToCharIntervalTest() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{-3, 1})));
        testData.add(new ArrayList<>(List.of(new Object[]{2, 3})));
        testData.add(new ArrayList<>(List.of(new Object[]{2, 3})));
        testData.add(new ArrayList<>(List.of(new Object[]{4, 5})));
        testData.add(new ArrayList<>(List.of(new Object[]{10, 13})));
        List<DigitInterval> expectedResultList = new ArrayList<>();
        expectedResultList.add(new DigitInterval(null,-3,1));
        expectedResultList.add(new DigitInterval(null,2,3));
        expectedResultList.add(new DigitInterval(null,2,3));
        expectedResultList.add(new DigitInterval(null,4,5));
        expectedResultList.add(new DigitInterval(null,10,13));

        List<DigitInterval> resultList = digitIntervalMapper.map(testData);
        Assertions.assertEquals(expectedResultList, resultList);
    }

    @Test
    void mapFromCharIntervalToListCharacterTest(){
        DigitInterval testInterval = new DigitInterval(null,-3, 1);
        List<Integer> expectedResultList = new ArrayList<>(List.of(new Integer[]{-3,1}));

        List<Integer> resultList = digitIntervalMapper.map(testInterval);
        Assertions.assertEquals(expectedResultList,resultList);
    }
}