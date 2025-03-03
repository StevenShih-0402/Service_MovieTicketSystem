package com.ctbcbank.navi.mid.movie.management.service.movieorders.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieOrderCreateRsBo {
    private BigInteger id;
    private BigInteger totalPrice;
}
