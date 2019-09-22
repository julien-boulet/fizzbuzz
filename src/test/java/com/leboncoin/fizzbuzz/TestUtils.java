package com.leboncoin.fizzbuzz;

import com.leboncoin.fizzbuzz.dto.GameParameter;
import com.leboncoin.fizzbuzz.dto.StatisticResult;
import com.leboncoin.fizzbuzz.entities.Statistic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TestUtils {

    public static int INT_1 = 3;
    public static int INT_2 = 5;
    public static int LIMIT = 15;
    public static String FIZZ = "fizz";
    public static String BUZZ = "buzz";

    public static List<String> EXPECTED_RESULT = Arrays.asList("1", "2", FIZZ, "4", BUZZ, FIZZ, "7", "8", FIZZ, BUZZ, "11", FIZZ, "13", "14", FIZZ + BUZZ);

    public static GameParameter PARAMS = GameParameter.builder().int1(TestUtils.INT_1).int2(TestUtils.INT_2).limit(TestUtils.LIMIT).str1(TestUtils.FIZZ).str2(TestUtils.BUZZ).build();
    public static Statistic STATISTIC = Statistic.builder().int1(TestUtils.INT_1).int2(TestUtils.INT_2).limit(TestUtils.LIMIT).str1(TestUtils.FIZZ).str2(TestUtils.BUZZ).build();
    public static StatisticResult STATISTICRESULT = StatisticResult.builder().int1(TestUtils.INT_1).int2(TestUtils.INT_2).limit(TestUtils.LIMIT).str1(TestUtils.FIZZ).str2(TestUtils.BUZZ).count(1).build();

    static Stream<String> blankStrings() {
        return Stream.of("", "   ", null);
    }
}
