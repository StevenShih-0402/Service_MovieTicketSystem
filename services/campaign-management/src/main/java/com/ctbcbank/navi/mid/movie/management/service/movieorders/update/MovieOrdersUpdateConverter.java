package com.ctbcbank.navi.mid.movie.management.service.movieorders.update;

import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderUpdateRq;
import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderUpdateRs;

public class MovieOrdersUpdateConverter {
    public static MovieOrderUpdateRqBo parseToBo(MovieOrderUpdateRq movieOrderUpdateRq){
        MovieOrderUpdateRqBo movieOrderUpdateRqBo = new MovieOrderUpdateRqBo();

        movieOrderUpdateRqBo.setId(movieOrderUpdateRq.getId());
        movieOrderUpdateRqBo.setMovieOrdersInfo(getMovieOrdersInfoBo(movieOrderUpdateRq.getMovieOrdersInfo()));

        return movieOrderUpdateRqBo;
    }

    private static MovieOrderUpdateRqBo.MovieOrdersInfoBo getMovieOrdersInfoBo(MovieOrderUpdateRq.MovieOrdersInfo movieOrdersInfo){
        MovieOrderUpdateRqBo.MovieOrdersInfoBo movieOrdersInfoBo = new MovieOrderUpdateRqBo.MovieOrdersInfoBo();

        movieOrdersInfoBo.setQuantity(movieOrdersInfo.getQuantity());

        return movieOrdersInfoBo;
    }

    public static MovieOrderUpdateRs parseToRs(MovieOrderUpdateRsBo movieOrderUpdateRsBo){
        MovieOrderUpdateRs movieOrderUpdateRs = new MovieOrderUpdateRs();

        movieOrderUpdateRs.setMovieOrderInfo(getMovieOrdersInfo(movieOrderUpdateRsBo.getMovieOrdersInfoBo()));

        return movieOrderUpdateRs;
    }

    private static MovieOrderUpdateRs.MovieOrderInfo getMovieOrdersInfo(MovieOrderUpdateRsBo.MovieOrdersInfoBo movieOrdersInfoBo){
        MovieOrderUpdateRs.MovieOrderInfo movieOrdersInfo = new MovieOrderUpdateRs.MovieOrderInfo();

        movieOrdersInfo.setQuantity(movieOrdersInfoBo.getQuantity());
        movieOrdersInfo.setTotalPrice(movieOrdersInfoBo.getTotalPrice());

        return movieOrdersInfo;
    }
}
