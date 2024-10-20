package ru.cft.shiftlabtesttask.core.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cft.shiftlabtesttask.core.exception.IntervalFormatException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DigitIntervalValidatorTest {

    @InjectMocks
    private DigitIntervalValidator digitIntervalValidator;

    @Test
    void validateTestSuccess() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{25, 30})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 4})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 4})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 5})));
        testData.add(new ArrayList<>(List.of(new Object[]{5, 6})));
        testData.add(new ArrayList<>(List.of(new Object[]{7, 8})));

        digitIntervalValidator.validate(testData);
    }

    @Test
    void validateTestFail1() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{25, 32})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 4})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, })));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 5})));
        testData.add(new ArrayList<>(List.of(new Object[]{5, 6})));
        testData.add(new ArrayList<>(List.of(new Object[]{7, 8})));

        Assertions.assertThrows(
            IntervalFormatException.class,
            ()->  digitIntervalValidator.validate(testData),
            "Все интервалы должны состоять из двух точек"
        );
    }

    @Test
    void validateTestFail2() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{25, 32})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 4})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 4})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 5, 7})));
        testData.add(new ArrayList<>(List.of(new Object[]{5, 6})));
        testData.add(new ArrayList<>(List.of(new Object[]{7, 8})));

        Assertions.assertThrows(
            IntervalFormatException.class,
            ()->  digitIntervalValidator.validate(testData),
            "Все интервалы должны состоять из двух точек"
        );
    }

    @Test
    void validateTestFail3() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{25, 32})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 4})));
        testData.add(new ArrayList<>(List.of(new Object[]{"a", 4})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 5})));
        testData.add(new ArrayList<>(List.of(new Object[]{5, 6})));
        testData.add(new ArrayList<>(List.of(new Object[]{7, 8})));

        Assertions.assertThrows(
            IntervalFormatException.class,
            ()->  digitIntervalValidator.validate(testData),
            "Интервал должен содержать только цифры"
        );
    }

    @Test
    void validateTestFail4() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{25, 32})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 4})));
        testData.add(new ArrayList<>(List.of(new Object[]{4, 4})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 5})));
        testData.add(new ArrayList<>(List.of(new Object[]{5, 6})));
        testData.add(new ArrayList<>(List.of(new Object[]{7, 8})));

        Assertions.assertThrows(
            IntervalFormatException.class,
            ()->  digitIntervalValidator.validate(testData),
            "Левая и правая граница должны быть различны"
        );
    }

    @Test
    void validateTestFail5() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{25, 32})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 4})));
        testData.add(new ArrayList<>(List.of(new Object[]{44, 4})));
        testData.add(new ArrayList<>(List.of(new Object[]{1, 5})));
        testData.add(new ArrayList<>(List.of(new Object[]{5, 6})));
        testData.add(new ArrayList<>(List.of(new Object[]{7, 8})));

        Assertions.assertThrows(
            IntervalFormatException.class,
            ()->  digitIntervalValidator.validate(testData),
            "Справа должно быть число больше чем слева"
        );
    }
}