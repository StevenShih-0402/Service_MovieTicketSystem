package com.ctbcbank.navi.mid.movie.management.service.movieorders.query;

import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderQueryRq;
import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderQueryRs;
import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieQueryRq;
import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieQueryRs;
import com.ctbcbank.navi.mid.movie.management.service.movies.query.MovieQueryRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.query.MovieQueryRsBo;

import java.util.List;

public class MovieOrdersQueryConverter {

    public static MovieOrderQueryRqBo parseToBo(MovieOrderQueryRq movieOrderQueryRq){
        MovieOrderQueryRqBo movieOrderQueryRqBo = new MovieOrderQueryRqBo();

        movieOrderQueryRqBo.setId(movieOrderQueryRq.getId());

        return movieOrderQueryRqBo;
    }

    public static MovieOrderQueryRs parseToRs(MovieOrderQueryRsBo movieOrderQueryRsBo){
        MovieOrderQueryRs movieOrderQueryRs = new MovieOrderQueryRs();

        movieOrderQueryRs.setMovieOrderInfoList(getMovieOrdersInfoList(movieOrderQueryRsBo.getMovieOrderInfoBo()));

        return movieOrderQueryRs;
    }

    private static List<MovieOrderQueryRs.MovieOrderInfo> getMovieOrdersInfoList(List<MovieOrderQueryRsBo.MovieOrderInfoBo> movieOrdersInfoBoList){
        List<MovieOrderQueryRs.MovieOrderInfo> movieOrdersInfoList = movieOrdersInfoBoList.stream().map(x ->{
            MovieOrderQueryRs.MovieOrderInfo movieOrdersInfo = new MovieOrderQueryRs.MovieOrderInfo();

            movieOrdersInfo.setMovieName(x.getMovieName());
            movieOrdersInfo.setUserName(x.getUserName());
            movieOrdersInfo.setQuantity(x.getQuantity());
            movieOrdersInfo.setTotalPrice(x.getTotalPrice());

            return movieOrdersInfo;
        }).toList();

        return movieOrdersInfoList;
    }
}
