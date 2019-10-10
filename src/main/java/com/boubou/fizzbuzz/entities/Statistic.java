package com.boubou.fizzbuzz.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistic {

    private static final long serialVersionUID = -5798016962040739254L;

    private String id;

    @PositiveOrZero
    private Integer int1;
    @PositiveOrZero
    private Integer int2;
    @Min(1)
    private Integer limit;
    @NotBlank
    private String str1;
    @NotBlank
    private String str2;
    @PositiveOrZero
    private Integer count;

    public Statistic(@PositiveOrZero Integer int1, @PositiveOrZero Integer int2, @Min(1) Integer limit, @NotBlank String str1, @NotBlank String str2,@PositiveOrZero Integer count) {
        this.int1 = int1;
        this.int2 = int2;
        this.limit = limit;
        this.str1 = str1;
        this.str2 = str2;
        this.count = count;
    }
}
