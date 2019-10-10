package com.boubou.fizzbuzz.services.impl;

import com.boubou.fizzbuzz.dto.GameParameter;
import com.boubou.fizzbuzz.dto.StatisticResult;
import com.boubou.fizzbuzz.repositories.StatisticRepository;
import com.boubou.fizzbuzz.services.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void save(GameParameter params) {
        statisticRepository.insertOrUpdate(params.getInt1(),params.getInt2(),params.getLimit(),params.getStr1(),params.getStr2());
        log.debug(() -> "save statistic");
    }

    /**
     * @see StatisticService#findMax()
     */
    @Override
    public Optional<StatisticResult> findMax() {
        return statisticRepository.findFirstByOrderByCountDesc().map(StatisticResult::new);
    }

}
