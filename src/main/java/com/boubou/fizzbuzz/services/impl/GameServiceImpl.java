package com.boubou.fizzbuzz.services.impl;

import com.boubou.fizzbuzz.dto.GameParameter;
import com.boubou.fizzbuzz.services.GameService;
import com.boubou.fizzbuzz.utils.CheckUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Log4j2
public class GameServiceImpl implements GameService {

    /**
     * @see GameService#compute(GameParameter)
     */
    @Override
    public List<String> compute(GameParameter params) {
        log.debug(() -> "compute with params : " + params.toString());

        // check if params are valid
        // I double check because maybe this method will be called from other than GameController
        var int1 = CheckUtil.checkInt(params.getInt1(), "Int1");
        var int2 = CheckUtil.checkInt(params.getInt2(), "Int2");
        var limit = CheckUtil.checkInt(params.getLimit(), "limit");

        var str1 = CheckUtil.checkString(params.getStr1(), "str1");
        var str2 = CheckUtil.checkString(params.getStr2(), "str2");

        // another solution exists with several ternary operators but I think this solution is clearer
        return IntStream.rangeClosed(1, limit).mapToObj(
                i -> {
                    if (i % int1 == 0 && i % int2 == 0) {
                        return str1 + str2;
                    } else if (i % int1 == 0) {
                        return str1;
                    } else if (i % int2 == 0) {
                        return str2;
                    } else {
                        return Integer.toString(i);
                    }
                }).collect(Collectors.toList());
    }
}
