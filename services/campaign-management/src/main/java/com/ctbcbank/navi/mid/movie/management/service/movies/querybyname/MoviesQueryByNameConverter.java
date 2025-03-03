package com.ctbcbank.navi.mid.movie.management.service.movies.querybyname;

import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieQueryByNameRq;
import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieQueryByNameRs;
import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieQueryRq;
import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieQueryRs;

import java.util.List;

public class MoviesQueryByNameConverter {
    public static MovieQueryByNameRqBo parseToBo(MovieQueryByNameRq movieQueryByNameRq){
        MovieQueryByNameRqBo movieQueryByNameRqBo = new MovieQueryByNameRqBo();

        movieQueryByNameRqBo.setKeyWord(movieQueryByNameRq.getKeyWord());
        movieQueryByNameRqBo.setPrice(movieQueryByNameRq.getPrice());
        movieQueryByNameRqBo.setPageNo(movieQueryByNameRq.getPageNo());

        return movieQueryByNameRqBo;
    }

    public static MovieQueryByNameRs parseToRs(MovieQueryByNameRsBo movieQueryByNameRsBo){
        MovieQueryByNameRs movieQueryByNameRs = new MovieQueryByNameRs();

        movieQueryByNameRs.setMoviesInfoList(getMoviesInfoList(movieQueryByNameRsBo.getMoviesInfoList()));

        return movieQueryByNameRs;
    }

    private static List<MovieQueryByNameRs.MoviesInfo> getMoviesInfoList(List<MovieQueryByNameRsBo.MoviesInfoBo> moviesInfoBoList){
        List<MovieQueryByNameRs.MoviesInfo> moviesInfoList = moviesInfoBoList.stream().map(x ->{
            MovieQueryByNameRs.MoviesInfo moviesInfo = new MovieQueryByNameRs.MoviesInfo();
            moviesInfo.setMovieName(x.getMovieName());
            moviesInfo.setPrice(x.getPrice());

            return moviesInfo;
        }).toList();

        return moviesInfoList;
    }
}
