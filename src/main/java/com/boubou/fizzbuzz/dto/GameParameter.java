package com.boubou.fizzbuzz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@Builder
public class GameParameter {

    private static final long serialVersionUID = 8612063150574534687L;

    @Positive
    private int int1;
    @Positive
    private int int2;
    @Positive
    private int limit;
    @NotBlank
    private String str1;
    @NotBlank
    private String str2;
}
