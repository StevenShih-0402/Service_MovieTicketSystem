package com.ctbcbank.navi.mid.movie.management.service.movies.query;

import com.ctbcbank.navi.mid.movie.management.dao.MoviesDao;
import com.ctbcbank.navi.mid.movie.management.dto.MovieDto;
import com.ctbcbank.navi.mid.movie.management.dto.QueryMovieConditionDto;
import com.ctbcbank.navi.mid.movie.management.repository.MoviesRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class MoviesQueryServiceImpl implements MoviesQueryService {

    private final String CLASS_NAME = MoviesQueryServiceImpl.class.getSimpleName();
    private final MoviesDao moviesDao;

    @Override
    @Transactional
    public MovieQueryRsBo query(MovieQueryRqBo movieQueryRqBo){
        QueryMovieConditionDto moviesConditionDto = new QueryMovieConditionDto();
        moviesConditionDto.setId(movieQueryRqBo.getId());

        if(!moviesDao.existsById(moviesConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。");
        }

        if(moviesDao.checkStatus(moviesConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此資料已被刪除，無法操作。");
        }

        List<MovieDto> movieDtoList = moviesDao.queryMovies(moviesConditionDto);

        log.info("[{}][query][movieDtoList.size: {}]", CLASS_NAME, movieDtoList.size());

        List<MovieQueryRsBo.MoviesInfoBo> moviesBoList = new ArrayList<>();
        movieDtoList.forEach(x ->{
            MovieQueryRsBo.MoviesInfoBo moviesBo = MovieQueryRsBo.MoviesInfoBo.builder()
                    .movieName(x.getMovieName())
                    .price(x.getPrice())
                    .build();
            moviesBoList.add(moviesBo);
        });
        MovieQueryRsBo movieQueryRsBo = new MovieQueryRsBo();
        movieQueryRsBo.setMoviesInfoList(moviesBoList);
        return movieQueryRsBo;
    }
}
