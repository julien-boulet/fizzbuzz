package com.boubou.fizzbuzz.services;

import com.boubou.fizzbuzz.dto.GameParameter;

import java.util.List;

public interface GameService {

    /**
     * Compute the FizzBuzz game with the given params
     *
     * @param params The given params
     * @return The List of strings with numbers from 1 to limit, where:
     * all multiples of int1 are replaced by str1, all multiples of int2 are replaced by str2,
     * all multiples of int1 and int2 are replaced by str1str2
     */
    List<String> compute(GameParameter params);
}
