package com.ctbcbank.navi.mid.movie.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto extends BaseDto{
    private String movieName;
    private BigInteger price;
}
