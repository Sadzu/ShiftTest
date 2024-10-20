package ru.cft.shiftlabtesttask.core.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cft.shiftlabtesttask.core.entities.DigitInterval;
import ru.cft.shiftlabtesttask.core.exception.IntervalNotFoundException;
import ru.cft.shiftlabtesttask.core.mapper.DigitIntervalMapper;
import ru.cft.shiftlabtesttask.core.repository.DigitIntervalRepository;
import ru.cft.shiftlabtesttask.core.validator.DigitIntervalValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class DigitIntervalServiceTest {
    @InjectMocks
    DigitIntervalService digitIntervalService;

    @Mock
    CombiningIntervalService<DigitInterval> combiningIntervalService;
    @Mock
    DigitIntervalMapper digitIntervalMapper;
    @Spy
    DigitIntervalValidator digitIntervalValidator;
    @Mock
    DigitIntervalRepository digitIntervalRepository;

    @Test
    void mergeTest(){
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{1,3})));
        testData.add(new ArrayList<>(List.of(new Object[]{1,3})));
        testData.add(new ArrayList<>(List.of(new Object[]{10,15})));
        testData.add(new ArrayList<>(List.of(new Object[]{13,18})));
        testData.add(new ArrayList<>(List.of(new Object[]{25,40})));

        List<DigitInterval> mappingResultList = new ArrayList<>();
        mappingResultList.add(new DigitInterval(null,1,3));
        mappingResultList.add(new DigitInterval(null,1,3));
        mappingResultList.add(new DigitInterval(null,10,15));
        mappingResultList.add(new DigitInterval(null,13,18));
        mappingResultList.add(new DigitInterval(null,25,40));

        List<DigitInterval> combiningResultList = new ArrayList<>();
        combiningResultList.add(new DigitInterval(null,1,3));
        combiningResultList.add(new DigitInterval(null,10,18));
        combiningResultList.add(new DigitInterval(null,25,40));

        when(digitIntervalMapper.map(testData)).thenReturn(mappingResultList);
        when(combiningIntervalService.combine(mappingResultList)).thenReturn(combiningResultList);
        when(digitIntervalRepository.saveAll(combiningResultList)).thenReturn(combiningResultList);

        digitIntervalService.merge(testData);
        verify(digitIntervalMapper).map(testData);
        verify(combiningIntervalService).combine(mappingResultList);
        verify(digitIntervalRepository).saveAll(combiningResultList);
    }

    @Test
    void getMinSuccess(){
        DigitInterval repositoryMinInterval = new DigitInterval(10,11,23);
        List<Integer> mappingMinInterval = new ArrayList<>(List.of(new Integer[]{11, 23}));
        when(digitIntervalRepository.findFirstByOrderByLeftBorderAscRightBorder())
            .thenReturn(Optional.of(repositoryMinInterval));
        when(digitIntervalMapper.map(repositoryMinInterval)).thenReturn(mappingMinInterval);

        List<Integer> testResult = digitIntervalService.getMin();
        Assertions.assertEquals(mappingMinInterval,testResult);
        verify(digitIntervalRepository).findFirstByOrderByLeftBorderAscRightBorder();
        verify(digitIntervalMapper).map(repositoryMinInterval);
    }

    @Test
    void getMinFail(){
        when(digitIntervalRepository.findFirstByOrderByLeftBorderAscRightBorder())
            .thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(
            IntervalNotFoundException.class,
            ()-> digitIntervalService.getMin(),
            "Интервал не был найден"
        );
        verify(digitIntervalRepository).findFirstByOrderByLeftBorderAscRightBorder();
    }
}