package ru.cft.shiftlabtesttask.core.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cft.shiftlabtesttask.core.exception.IntervalFormatException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CharacterIntervalValidatorTest {

    @InjectMocks
    private CharacterIntervalValidator characterIntervalValidator;

    @Test
    void validateTestSuccess() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"c", "g"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"d", "h"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"q", "R"})));

        characterIntervalValidator.validate(testData);
    }

    @Test
    void validateTestFail1() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{"a", })));
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"c", "g"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"d", "h"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"q", "R"})));

        Assertions.assertThrows(
          IntervalFormatException.class,
            ()->  characterIntervalValidator.validate(testData),
            "Все интервалы должны состоять из двух точек"
        );
    }

    @Test
    void validateTestFail2() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b", "c"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"c", "g"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"d", "h"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"q", "R"})));

        Assertions.assertThrows(
            IntervalFormatException.class,
            ()->  characterIntervalValidator.validate(testData),
            "Все интервалы должны состоять из двух точек"
        );
    }

    @Test
    void validateTestFail3() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "aa"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"c", "g"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"d", "h"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"q", "R"})));

        Assertions.assertThrows(
            IntervalFormatException.class,
            ()->  characterIntervalValidator.validate(testData),
            "Границы интервала могут состоять только из одной буквы английского алфавита"
        );
    }

    @Test
    void validateTestFail4() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "!"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"c", "g"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"d", "h"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"q", "R"})));

        Assertions.assertThrows(
            IntervalFormatException.class,
            ()->  characterIntervalValidator.validate(testData),
            "Границы интервала могут состоять только из одной буквы английского алфавита"
        );
    }

    @Test
    void validateTestFail5() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new String[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new String[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new String[]{"c", "g"})));
        testData.add(new ArrayList<>(List.of(new String[]{"d", "ч"})));
        testData.add(new ArrayList<>(List.of(new String[]{"q", "R"})));

        Assertions.assertThrows(
            IntervalFormatException.class,
            ()->  characterIntervalValidator.validate(testData),
            "Границы интервала могут состоять только из одной буквы английского алфавита"
        );
    }

    @Test
    void validateTestFail6() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new String[]{"a", "a"})));
        testData.add(new ArrayList<>(List.of(new String[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new String[]{"c", "g"})));
        testData.add(new ArrayList<>(List.of(new String[]{"d", "h"})));
        testData.add(new ArrayList<>(List.of(new String[]{"q", "R"})));

        Assertions.assertThrows(
            IntervalFormatException.class,
            ()->  characterIntervalValidator.validate(testData),
            "Левая и правая граница должны быть различны"
        );
    }

    @Test
    void validateTestFail7() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new String[]{"b", "a"})));
        testData.add(new ArrayList<>(List.of(new String[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new String[]{"c", "g"})));
        testData.add(new ArrayList<>(List.of(new String[]{"d", "h"})));
        testData.add(new ArrayList<>(List.of(new String[]{"q", "R"})));

        Assertions.assertThrows(
            IntervalFormatException.class,
            ()->  characterIntervalValidator.validate(testData),
            "Справа должно быть символ больше чем слева"
        );
    }
}