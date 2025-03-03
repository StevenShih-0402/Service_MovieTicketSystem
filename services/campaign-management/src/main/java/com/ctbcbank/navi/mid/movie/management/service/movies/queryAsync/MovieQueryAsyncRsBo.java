package com.ctbcbank.navi.mid.movie.management.service.movies.queryAsync;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieQueryAsyncRsBo {

    private List<MovieOrderInfoBo> movieOrderInfoListBo;
    private List<MovieInfoBo> movieInfoListBo;
    private List<UserInfoBo> userInfoListBo;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieOrderInfoBo{

        private BigInteger movieOrderId;
        private BigInteger quantity;
        private BigInteger totalPrice;
        private BigInteger userId;
        private BigInteger movieId;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieInfoBo{

        private BigInteger movieId;
        private String movieName;
        private BigInteger price;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoBo{

        private BigInteger userId;
        private String userName;
        private String email;
    }
}
