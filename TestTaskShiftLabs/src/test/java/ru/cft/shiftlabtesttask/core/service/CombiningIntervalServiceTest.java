package ru.cft.shiftlabtesttask.core.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cft.shiftlabtesttask.core.entities.CharInterval;
import ru.cft.shiftlabtesttask.core.entities.DigitInterval;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CombiningIntervalServiceTest {

    @InjectMocks
    CombiningIntervalService<CharInterval> charIntervalCombiningIntervalService;

    @InjectMocks
    CombiningIntervalService<DigitInterval> digitIntervalCombiningIntervalService;

    @Test
    void combineCharacterTestSuccess1() {
        List<CharInterval> charIntervals = new ArrayList<>();
        charIntervals.add(new CharInterval(0, 'a', 'b'));
        List<CharInterval> resultList = charIntervalCombiningIntervalService.combine(charIntervals);

        Assertions.assertEquals(charIntervals, resultList);
    }

    @Test
    void combineCharacterTestSuccess2() {
        List<CharInterval> charIntervals = new ArrayList<>();
        List<CharInterval> resultList = charIntervalCombiningIntervalService.combine(charIntervals);

        Assertions.assertNull(resultList);
    }

    @Test
    void combineCharacterTestSuccess3() {
        List<CharInterval> charIntervals = new ArrayList<>();
        charIntervals.add(new CharInterval(null, 'a', 'c'));
        charIntervals.add(new CharInterval(null, 'b', 'd'));
        charIntervals.add(new CharInterval(null, 'e', 'g'));
        List<CharInterval> resultList = charIntervalCombiningIntervalService.combine(charIntervals);
        List<CharInterval> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CharInterval(null, 'a', 'd'));
        expectedResultList.add(new CharInterval(null, 'e', 'g'));

        Assertions.assertEquals(expectedResultList, resultList);
    }

    @Test
    void combineCharacterTestSuccess4() {
        List<CharInterval> charIntervals = new ArrayList<>();
        charIntervals.add(new CharInterval(null, 'a', 'c'));
        charIntervals.add(new CharInterval(null, 'c', 'd'));
        charIntervals.add(new CharInterval(null, 'e', 'g'));
        List<CharInterval> resultList = charIntervalCombiningIntervalService.combine(charIntervals);
        List<CharInterval> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CharInterval(null, 'a', 'd'));
        expectedResultList.add(new CharInterval(null, 'e', 'g'));

        Assertions.assertEquals(expectedResultList, resultList);
    }

    @Test
    void combineCharacterTestSuccess5() {
        List<CharInterval> charIntervals = new ArrayList<>();
        charIntervals.add(new CharInterval(null, 'e', 'g'));
        charIntervals.add(new CharInterval(null, 'a', 'c'));
        charIntervals.add(new CharInterval(null, 'c', 'd'));
        List<CharInterval> resultList = charIntervalCombiningIntervalService.combine(charIntervals);
        List<CharInterval> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CharInterval(null, 'a', 'd'));
        expectedResultList.add(new CharInterval(null, 'e', 'g'));

        Assertions.assertEquals(expectedResultList, resultList);
    }

    @Test
    void combineCharacterTestSuccess6() {
        List<CharInterval> charIntervals = new ArrayList<>();
        charIntervals.add(new CharInterval(null, 'a', 'c'));
        charIntervals.add(new CharInterval(null, 'a', 'c'));
        charIntervals.add(new CharInterval(null, 'c', 'd'));
        charIntervals.add(new CharInterval(null, 'q', 'r'));
        List<CharInterval> resultList = charIntervalCombiningIntervalService.combine(charIntervals);
        List<CharInterval> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CharInterval(null, 'a', 'd'));
        expectedResultList.add(new CharInterval(null, 'q', 'r'));

        Assertions.assertEquals(expectedResultList, resultList);
    }

    @Test
    void combineDigitTestSuccess1() {
        List<DigitInterval> digitIntervals = new ArrayList<>();
        digitIntervals.add(new DigitInterval(null, -3, 1));
        List<DigitInterval> resultList = digitIntervalCombiningIntervalService.combine(digitIntervals);

        Assertions.assertEquals(digitIntervals, resultList);
    }

    @Test
    void combineDigitTestSuccess2() {
        List<DigitInterval> digitIntervals = new ArrayList<>();
        List<DigitInterval> resultList = digitIntervalCombiningIntervalService.combine(digitIntervals);

        Assertions.assertNull(resultList);
    }

    @Test
    void combineDigitTestSuccess3() {
        List<DigitInterval> digitIntervals = new ArrayList<>();
        digitIntervals.add(new DigitInterval(null, -3, 1));
        digitIntervals.add(new DigitInterval(null, 2, 4));
        digitIntervals.add(new DigitInterval(null, 3, 5));
        digitIntervals.add(new DigitInterval(null, 11, 23));
        List<DigitInterval> resultList = digitIntervalCombiningIntervalService.combine(digitIntervals);
        List<DigitInterval> expectedResultList = new ArrayList<>();
        expectedResultList.add(new DigitInterval(null, -3, 1));
        expectedResultList.add(new DigitInterval(null, 2, 5));
        expectedResultList.add(new DigitInterval(null, 11, 23));
        Assertions.assertEquals(expectedResultList, resultList);
    }

    @Test
    void combineDigitTestSuccess4() {
        List<DigitInterval> digitIntervals = new ArrayList<>();
        digitIntervals.add(new DigitInterval(null, 22, 28));
        digitIntervals.add(new DigitInterval(null, -3, 1));
        digitIntervals.add(new DigitInterval(null, 2, 4));
        digitIntervals.add(new DigitInterval(null, 3, 5));
        digitIntervals.add(new DigitInterval(null, 11, 23));
        List<DigitInterval> resultList = digitIntervalCombiningIntervalService.combine(digitIntervals);
        List<DigitInterval> expectedResultList = new ArrayList<>();
        expectedResultList.add(new DigitInterval(null, -3, 1));
        expectedResultList.add(new DigitInterval(null, 2, 5));
        expectedResultList.add(new DigitInterval(null, 11, 28));
        Assertions.assertEquals(expectedResultList, resultList);
    }

    @Test
    void combineDigitTestSuccess5() {
        List<DigitInterval> digitIntervals = new ArrayList<>();
        digitIntervals.add(new DigitInterval(null, -3, 1));
        digitIntervals.add(new DigitInterval(null, 1, 2));
        digitIntervals.add(new DigitInterval(null, 2, 4));
        digitIntervals.add(new DigitInterval(null, 3, 5));
        digitIntervals.add(new DigitInterval(null, 11, 23));
        List<DigitInterval> resultList = digitIntervalCombiningIntervalService.combine(digitIntervals);
        List<DigitInterval> expectedResultList = new ArrayList<>();
        expectedResultList.add(new DigitInterval(null, -3, 5));
        expectedResultList.add(new DigitInterval(null, 11, 23));
        Assertions.assertEquals(expectedResultList, resultList);
    }

    @Test
    void combineDigitTestSuccess6() {
        List<DigitInterval> digitIntervals = new ArrayList<>();
        digitIntervals.add(new DigitInterval(null, -3, 1));
        digitIntervals.add(new DigitInterval(null, -3, 1));
        digitIntervals.add(new DigitInterval(null, 1, 2));
        digitIntervals.add(new DigitInterval(null, 2, 4));
        digitIntervals.add(new DigitInterval(null, 3, 5));
        digitIntervals.add(new DigitInterval(null, 11, 23));
        List<DigitInterval> resultList = digitIntervalCombiningIntervalService.combine(digitIntervals);
        List<DigitInterval> expectedResultList = new ArrayList<>();
        expectedResultList.add(new DigitInterval(null, -3, 5));
        expectedResultList.add(new DigitInterval(null, 11, 23));
        Assertions.assertEquals(expectedResultList, resultList);
    }
}