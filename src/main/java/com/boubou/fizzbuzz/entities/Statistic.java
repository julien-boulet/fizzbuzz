package com.boubou.fizzbuzz.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistic {

    private static final long serialVersionUID = -5798016962040739254L;

    @Id
    @GeneratedValue(generator = "guid")
    @GenericGenerator(name = "guid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @PositiveOrZero
    private Integer int1;
    @PositiveOrZero
    private Integer int2;
    @Min(1)
    // limit is a sql reserved word
    @Column(name = "lim")
    private Integer limit;
    @NotBlank
    private String str1;
    @NotBlank
    private String str2;
    @PositiveOrZero
    private Integer count;

}
