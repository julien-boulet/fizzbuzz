package com.boubou.fizzbuzz.services.impl;

import com.boubou.fizzbuzz.TestUtils;
import com.boubou.fizzbuzz.dto.GameParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameServiceImplTest {

    private GameServiceImpl gameServiceImpl;

    @BeforeEach
    void setUp() {
        gameServiceImpl = new GameServiceImpl();
    }

    @Test
    void testCompute() throws IllegalArgumentException {
        // Run the test
        final List<String> result = gameServiceImpl.compute(TestUtils.PARAMS);

        // Verify the results
        assertEquals(TestUtils.EXPECTED_RESULT, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, 0})
    void testComputeErrorInt1(final Integer int1) {
        final GameParameter params = GameParameter.builder().int1(int1).int2(TestUtils.INT_2).limit(TestUtils.LIMIT).str1(TestUtils.FIZZ).str2(TestUtils.BUZZ).build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> gameServiceImpl.compute(params));

        assertEquals(exception.toString(), String.format("java.lang.IllegalArgumentException: 'Int1=%d' must be greater than 0", params.getInt1()));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, 0})
    void testComputeErrorInt2(final Integer int2) {
        final GameParameter params = GameParameter.builder().int1(TestUtils.INT_1).int2(int2).limit(TestUtils.LIMIT).str1(TestUtils.FIZZ).str2(TestUtils.BUZZ).build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> gameServiceImpl.compute(params));

        assertEquals(exception.toString(), String.format("java.lang.IllegalArgumentException: 'Int2=%d' must be greater than 0", params.getInt2()));
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 0})
    void testComputeErrorLimit(final Integer limit) {
        final GameParameter params = GameParameter.builder().int1(TestUtils.INT_1).int2(TestUtils.INT_2).limit(limit).str1(TestUtils.FIZZ).str2(TestUtils.BUZZ).build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> gameServiceImpl.compute(params));

        assertEquals(exception.toString(), String.format("java.lang.IllegalArgumentException: 'limit=%d' must be greater than 0", params.getLimit()));
    }

    @ParameterizedTest
    @MethodSource("com.boubou.fizzbuzz.TestUtils#blankStrings")
    void testComputeErrorStr1(final String str1) {
        final GameParameter params = GameParameter.builder().int1(TestUtils.INT_1).int2(TestUtils.INT_2).limit(TestUtils.LIMIT).str1(str1).str2(TestUtils.BUZZ).build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> gameServiceImpl.compute(params));

        assertEquals(exception.toString(), String.format("java.lang.IllegalArgumentException: 'str1=%s' must not be blank", params.getStr1()));
    }

    @ParameterizedTest
    @MethodSource("com.boubou.fizzbuzz.TestUtils#blankStrings")
    void testComputeErrorStr2(final String str2) {
        final GameParameter params = GameParameter.builder().int1(TestUtils.INT_1).int2(TestUtils.INT_2).limit(TestUtils.LIMIT).str1(TestUtils.FIZZ).str2(str2).build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> gameServiceImpl.compute(params));

        assertEquals(exception.toString(), String.format("java.lang.IllegalArgumentException: 'str2=%s' must not be blank", params.getStr2()));
    }
}
