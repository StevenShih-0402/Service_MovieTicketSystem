package com.ctbcbank.navi.mid.movie.management.service.movieorders.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieOrderUpdateRsBo {

    private MovieOrdersInfoBo movieOrdersInfoBo;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieOrdersInfoBo{

        private BigInteger quantity;
        private BigInteger totalPrice;
    }
}
