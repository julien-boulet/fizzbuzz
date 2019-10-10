package com.boubou.fizzbuzz.repositories;

import com.boubou.fizzbuzz.entities.Statistic;

import java.util.Optional;

public interface StatisticRepository {

    Optional<Statistic> findFirstByOrderByCountDesc();

    int insertOrUpdate(int int1, int int2, int limit, String str1, String str2);
}
