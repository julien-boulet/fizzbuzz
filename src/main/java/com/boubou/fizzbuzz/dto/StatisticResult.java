package com.boubou.fizzbuzz.dto;

import com.boubou.fizzbuzz.entities.Statistic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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

    public StatisticResult(Statistic statistic, ModelMapper modelMapper) {
        modelMapper.map(statistic,this);
    }
}
