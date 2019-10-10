package com.boubou.fizzbuzz.services.impl;

import com.boubou.fizzbuzz.dto.StatisticResult;
import com.boubou.fizzbuzz.entities.Statistic;
import com.boubou.fizzbuzz.repositories.StatisticRepository;
import com.boubou.fizzbuzz.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class StatisticServiceImplTest {

    @Mock
    private StatisticRepository mockStatisticRepository;
    private StatisticServiceImpl statisticServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        statisticServiceImplUnderTest = new StatisticServiceImpl(mockStatisticRepository);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 55, 60})
    void testSaveElement(int count) {
        // mock
        when(mockStatisticRepository.insertOrUpdate(anyInt(), anyInt(), anyInt(), anyString(), anyString())).thenReturn(0);

        // Run the test
        statisticServiceImplUnderTest.save(TestUtils.PARAMS);

        // Verify the results
        verify(mockStatisticRepository, times(1)).insertOrUpdate(anyInt(), anyInt(), anyInt(), anyString(), anyString());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 55, 60})
    void testFindMax(int count) {
        // Setup
        TestUtils.STATISTIC.setCount(count);
        TestUtils.STATISTICRESULT.setCount(count);

        when(mockStatisticRepository.findFirstByOrderByCountDesc()).thenReturn(Optional.of(TestUtils.STATISTIC));

        // Run the test
        final Optional<StatisticResult> result = statisticServiceImplUnderTest.findMax();

        // Verify the results
        assertEquals(Optional.of(TestUtils.STATISTICRESULT), result);
    }
}
