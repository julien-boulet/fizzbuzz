package com.leboncoin.fizzbuzz.repositories;

import com.leboncoin.fizzbuzz.entities.Statistic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatisticRepository extends CrudRepository<Statistic, String> {

    Optional<Statistic> findByInt1AndInt2AndLimitAndStr1AndStr2(Integer int1, Integer int2, Integer lim, String str1, String str2);

    Optional<Statistic> findFirstByOrderByCountDesc();

}
