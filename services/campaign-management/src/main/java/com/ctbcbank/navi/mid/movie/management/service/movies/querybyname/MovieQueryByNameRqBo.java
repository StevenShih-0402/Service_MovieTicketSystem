package com.ctbcbank.navi.mid.movie.management.service.movies.querybyname;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieQueryByNameRqBo {
    private String keyWord;
    private BigInteger price;
    private BigInteger pageNo;
}
