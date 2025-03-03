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
public class MovieOrderDto extends BaseDto{
    private BigInteger quantity;
    private BigInteger movieId;
    private BigInteger userId;
    private BigInteger totalPrice;
}
