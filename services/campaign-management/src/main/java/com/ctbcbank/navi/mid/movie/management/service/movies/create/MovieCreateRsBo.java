package com.ctbcbank.navi.mid.movie.management.service.movies.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCreateRsBo {
    private BigInteger id;
    private String movieName;
}
