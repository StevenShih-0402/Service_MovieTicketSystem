package com.ctbcbank.navi.mid.movie.management.service.movies.queryAsync;

import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieQueryAsyncRs;

import java.util.List;

public class MoviesQueryAsyncConverter {

    public static MovieQueryAsyncRs parseToRs(MovieQueryAsyncRsBo moviesQueryAsyncRsBo){
        MovieQueryAsyncRs movieQueryAsyncRs = new MovieQueryAsyncRs();

        movieQueryAsyncRs.setMovieOrderInfoList(getMovieOrderInfo(moviesQueryAsyncRsBo.getMovieOrderInfoListBo()));
        movieQueryAsyncRs.setMovieInfoList(getMovieInfo(moviesQueryAsyncRsBo.getMovieInfoListBo()));
        movieQueryAsyncRs.setUserInfoList(getUserInfo(moviesQueryAsyncRsBo.getUserInfoListBo()));

        return movieQueryAsyncRs;
    }

    private static List<MovieQueryAsyncRs.MovieOrderInfo> getMovieOrderInfo(List<MovieQueryAsyncRsBo.MovieOrderInfoBo> movieOrderInfoBoList){

        return movieOrderInfoBoList.stream().map(x ->{
            MovieQueryAsyncRs.MovieOrderInfo movieOrderInfo = new MovieQueryAsyncRs.MovieOrderInfo();
            movieOrderInfo.setMovieOrderId(x.getMovieOrderId());
            movieOrderInfo.setUserId(x.getUserId());
            movieOrderInfo.setMovieId(x.getMovieId());
            movieOrderInfo.setQuantity(x.getQuantity());
            movieOrderInfo.setTotalPrice(x.getTotalPrice());

            return movieOrderInfo;
        }).toList();
    }

    private static List<MovieQueryAsyncRs.MovieInfo> getMovieInfo(List<MovieQueryAsyncRsBo.MovieInfoBo> movieInfoBoList){

        return movieInfoBoList.stream().map(x ->{
            MovieQueryAsyncRs.MovieInfo movieInfo = new MovieQueryAsyncRs.MovieInfo();
            movieInfo.setMovieId(x.getMovieId());
            movieInfo.setMovieName(x.getMovieName());
            movieInfo.setPrice(x.getPrice());

            return movieInfo;
        }).toList();
    }

    private static List<MovieQueryAsyncRs.UserInfo> getUserInfo(List<MovieQueryAsyncRsBo.UserInfoBo> userInfoBoList){

        return userInfoBoList.stream().map(x ->{
            MovieQueryAsyncRs.UserInfo userInfo = new MovieQueryAsyncRs.UserInfo();

            userInfo.setUserId(x.getUserId());
            userInfo.setUserName(x.getUserName());
            userInfo.setEmail(x.getEmail());

            return userInfo;
        }).toList();
    }
}