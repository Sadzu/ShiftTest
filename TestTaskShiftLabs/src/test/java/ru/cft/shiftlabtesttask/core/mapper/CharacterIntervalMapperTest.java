package ru.cft.shiftlabtesttask.core.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cft.shiftlabtesttask.core.entities.CharInterval;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CharacterIntervalMapperTest {

    @InjectMocks
    CharacterIntervalMapper characterIntervalMapper;

    @Test
    void mapFromObjectListToCharIntervalTest() {
        List<List<Object>> testData = new ArrayList<>();
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"a", "b"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"c", "g"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"d", "h"})));
        testData.add(new ArrayList<>(List.of(new Object[]{"q", "R"})));
        List<CharInterval> expectedResultList = new ArrayList<>();
        expectedResultList.add(new CharInterval(null,'a','b'));
        expectedResultList.add(new CharInterval(null,'a','b'));
        expectedResultList.add(new CharInterval(null,'c','g'));
        expectedResultList.add(new CharInterval(null,'d','h'));
        expectedResultList.add(new CharInterval(null,'q','r'));

        List<CharInterval> resultList = characterIntervalMapper.map(testData);
        Assertions.assertEquals(expectedResultList, resultList);
    }

    @Test
    void mapFromCharIntervalToListCharacterTest(){
        CharInterval testInterval = new CharInterval(null,'a', 'b');
        List<Character> expectedResultList = new ArrayList<>(List.of(new Character[]{'a','b'}));

        List<Character> resultList = characterIntervalMapper.map(testInterval);
        Assertions.assertEquals(expectedResultList,resultList);
    }
}