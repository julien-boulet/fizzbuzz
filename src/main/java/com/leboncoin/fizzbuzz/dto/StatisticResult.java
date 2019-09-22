package com.leboncoin.fizzbuzz.dto;

import com.leboncoin.fizzbuzz.entities.Statistic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticResult implements Serializable {

    private static final long serialVersionUID = 5218955488493001713L;

    private Integer int1;
    private Integer int2;
    private Integer limit;
    private String str1;
    private String str2;
    private Integer count;

    public StatisticResult(Statistic statistic) {
        this.int1 = statistic.getInt1();
        this.int2 = statistic.getInt2();
        this.limit = statistic.getLimit();
        this.str1 = statistic.getStr1();
        this.str2 = statistic.getStr2();
        this.count = statistic.getCount();
    }
}
