package com.boubou.fizzbuzz.services.impl;

import com.boubou.fizzbuzz.dto.GameParameter;
import com.boubou.fizzbuzz.dto.StatisticResult;
import com.boubou.fizzbuzz.entities.Statistic;
import com.boubou.fizzbuzz.repositories.StatisticRepository;
import com.boubou.fizzbuzz.services.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;

    /**
     * @see StatisticService#save(GameParameter)
     */
    @Override
    public Statistic save(GameParameter params) {
        Optional<Statistic> statisticOptional = statisticRepository.findByInt1AndInt2AndLimitAndStr1AndStr2(
                params.getInt1(), params.getInt2(), params.getLimit(), params.getStr1(), params.getStr2());

        Statistic statistic = statisticOptional.orElse(Statistic.builder().int1(params.getInt1()).int2(params.getInt2())
                .limit(params.getLimit()).str1(params.getStr1()).str2(params.getStr2()).count(0).build());
        statistic.setCount(statistic.getCount() + 1);

        Statistic statisticDB = statisticRepository.save(statistic);
        log.debug(() -> "save statistic : " + statisticDB.toString());
        return statisticDB;
    }

    /**
     * @see StatisticService#findMax()
     */
    @Override
    public Optional<StatisticResult> findMax() {
        return statisticRepository.findFirstByOrderByCountDesc().map(StatisticResult::new);
    }

}
