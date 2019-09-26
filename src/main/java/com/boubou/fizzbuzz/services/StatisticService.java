package com.boubou.fizzbuzz.services;

import com.boubou.fizzbuzz.dto.GameParameter;
import com.boubou.fizzbuzz.dto.StatisticResult;
import com.boubou.fizzbuzz.entities.Statistic;

import java.util.Optional;

public interface StatisticService {

    /**
     * Save the given params in Database.
     * If the given params already exist, the count is increment,
     * else a new line is save in database with count to 1.
     *
     * @param params the given params to save or increment
     * @return the database Statistic element
     */
    Statistic save(GameParameter params);

    /**
     * Find the database entity with the higher count
     *
     * @return The database entity result with the higher count
     */
    Optional<StatisticResult> findMax();

}
