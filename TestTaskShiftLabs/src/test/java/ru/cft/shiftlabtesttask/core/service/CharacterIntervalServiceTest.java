package ru.cft.shiftlabtesttask.core.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cft.shiftlabtesttask.core.entities.CharInterval;
import ru.cft.shiftlabtesttask.core.exception.IntervalNotFoundException;
import ru.cft.shiftlabtesttask.core.mapper.CharacterIntervalMapper;
import ru.cft.shiftlabtesttask.core.repository.CharacterIntervalRepository;
import ru.cft.shiftlabtesttask.core.validator.CharacterIntervalValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CharacterIntervalServiceTest {

    @InjectMocks
    CharacterIntervalService characterIntervalService;

    @Mock
    CombiningIntervalService<CharInterval> combiningIntervalService;
    @Mock
    CharacterIntervalMapper characterIntervalMapper;
    @Spy
    CharacterIntervalValidator characterIntervalValidator;
    @Mock
    CharacterIntervalRepository characterIntervalRepository;

    @Test
    void mergeTest(){
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"c", "g"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"d", "h"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"q", "R"})));

        List<CharInterval> mappingResultList = new ArrayList<>();
        mappingResultList.add(new CharInterval(null,'a','b'));
        mappingResultList.add(new CharInterval(null,'a','b'));
        mappingResultList.add(new CharInterval(null,'c','g'));
        mappingResultList.add(new CharInterval(null,'d','h'));
        mappingResultList.add(new CharInterval(null,'q','r'));

        List<CharInterval> combiningResultList = new ArrayList<>();
        combiningResultList.add(new CharInterval(null,'a','b'));
        combiningResultList.add(new CharInterval(null,'c','h'));
        combiningResultList.add(new CharInterval(null,'q','r'));

        when(characterIntervalMapper.map(testData)).thenReturn(mappingResultList);
        when(combiningIntervalService.combine(mappingResultList)).thenReturn(combiningResultList);
        when(characterIntervalRepository.saveAll(combiningResultList)).thenReturn(combiningResultList);

        characterIntervalService.merge(testData);
        verify(characterIntervalMapper).map(testData);
        verify(combiningIntervalService).combine(mappingResultList);
        verify(characterIntervalRepository).saveAll(combiningResultList);
    }

    @Test
    void getMinSuccess(){
        CharInterval repositoryMinInterval = new CharInterval(10,'a','b');
        List<Character> mappingMinInterval = new ArrayList<>(List.of(new Character[]{'a', 'b'}));
        when(characterIntervalRepository.findFirstByOrderByLeftBorderAscRightBorder())
            .thenReturn(Optional.of(repositoryMinInterval));
        when(characterIntervalMapper.map(repositoryMinInterval)).thenReturn(mappingMinInterval);

        List<Character> testResult = characterIntervalService.getMin();
        Assertions.assertEquals(mappingMinInterval,testResult);
        verify(characterIntervalRepository).findFirstByOrderByLeftBorderAscRightBorder();
        verify(characterIntervalMapper).map(repositoryMinInterval);
    }

    @Test
    void getMinFail(){
        when(characterIntervalRepository.findFirstByOrderByLeftBorderAscRightBorder())
            .thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(
            IntervalNotFoundException.class,
            ()->characterIntervalService.getMin(),
            "Интервал не был найден"
        );
        verify(characterIntervalRepository).findFirstByOrderByLeftBorderAscRightBorder();
    }
}