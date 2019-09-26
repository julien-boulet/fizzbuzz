package com.boubou.fizzbuzz.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckUtilTest {

    @ParameterizedTest
    @ValueSource(strings = {"test", "t", "1"})
    void testCheckString(String value) {
        // Setup
        final String message = "message";

        // Run the test
        final String result = CheckUtil.checkString(value, message);

        // Verify the results
        assertEquals(value, result);
    }

    @ParameterizedTest
    @MethodSource("com.boubou.fizzbuzz.TestUtils#blankStrings")
    void testCheckStringWithError(String value) {
        // Setup
        final String message = "message";

        // Run the test
        assertThrows(IllegalArgumentException.class, () -> CheckUtil.checkString(value, message));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 23})
    void testCheckInt(Integer value) {
        // Setup
        final String message = "message";

        // Run the test
        final Integer result = CheckUtil.checkInt(value, message);

        // Verify the results
        assertEquals(value, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -23})
    void testCheckIntWithError(Integer value) {
        // Setup
        final String message = "message";

        // Run the test
        assertThrows(IllegalArgumentException.class, () -> CheckUtil.checkInt(value, message));
    }
}
