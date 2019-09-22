package com.leboncoin.fizzbuzz.utils;

import java.util.Optional;
import java.util.function.Predicate;

public class CheckUtil {

    //Add a private constructor to hide the implicit public one
    private CheckUtil(){ }

    /**
     * Check if the given String Value is not blank
     *
     * @param value   The given value
     * @param message The message to display in Exception
     * @return The given value if correct
     * @throws IllegalArgumentException when the string value is blank
     */
    public static String checkString(String value, String message) {
        return Optional.ofNullable(value).filter(Predicate.not(String::isBlank)).orElseThrow(() -> new IllegalArgumentException(String.format("'%s=%s' must not be blank", message, value)));
    }

    /**
     * Check if the given Integer Value is positive ( over 0)
     *
     * @param value   The given value
     * @param message The message to display in Exception
     * @return The given value if correct
     * @throws IllegalArgumentException when the Integer doesn't respect the predicate
     */
    public static Integer checkInt(int value, String message) {
        return Optional.of(value).filter(n -> n > 0).orElseThrow(() -> new IllegalArgumentException(String.format("'%s=%d' must be greater than 0", message, value)));
    }
}
