package com.leboncoin.fizzbuzz.controllers;

import com.leboncoin.fizzbuzz.dto.GameParameter;
import com.leboncoin.fizzbuzz.dto.StatisticResult;
import com.leboncoin.fizzbuzz.services.GameService;
import com.leboncoin.fizzbuzz.services.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/${application.versionApi}/")
@RequiredArgsConstructor
@Log4j2
public class GameController {

    private final GameService gameService;
    private final StatisticService statisticService;

    /**
     * Endpoint to play the fizzbuzz game and save the given params in database
     *
     * @param params The game params
     * @return The List of strings with numbers from 1 to limit, where:
     * all multiples of int1 are replaced by str1, all multiples of int2 are replaced by str2,
     * all multiples of int1 and int2 are replaced by str1str2
     */
    @GetMapping("fizzbuzz")
    public List<String> fizzBuzz(final @Valid GameParameter params) {
        log.debug(() -> "call GET endpoint fizzbuzz with params : " + params.toString());

        var resultList = gameService.compute(params);
        statisticService.save(params);
        return resultList;
    }

    /**
     * Endpoint to know what the most frequent request has been
     *
     * @return The parameters corresponding to the most used request, as well as the number of hits for this request
     */
    @GetMapping("oneTopStatistic")
    public ResponseEntity<StatisticResult> oneTopStatistic() {
        log.debug(() -> "call GET endpoint oneTopStatistic");
        return statisticService.findMax().map(ResponseEntity::ok).orElseGet(ResponseEntity.notFound()::build);
    }

}
