package com.ctbcbank.navi.mid.movie.management.service.movies.update;

import com.ctbcbank.navi.mid.movie.management.dao.MoviesDao;
import com.ctbcbank.navi.mid.movie.management.dto.MovieDto;
import com.ctbcbank.navi.mid.movie.management.dto.QueryMovieConditionDto;
import com.ctbcbank.navi.mid.movie.management.repository.MoviesRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

import java.math.BigInteger;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class MoviesUpdateServiceImpl implements MoviesUpdateService{

    private final String CLASS_NAME = MoviesUpdateServiceImpl.class.getSimpleName();
    private final MoviesDao moviesDao;

    @Override
    public MovieUpdateRsBo update(MovieUpdateRqBo movieUpdateRqBo){

        BigInteger movieId = movieUpdateRqBo.getId();

        if(moviesDao.existsByMovieNameExceptId(movieUpdateRqBo.getMoviesInfo().getMovieName(), movieUpdateRqBo.getId())){
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "電影名稱不能重複。");
        }
        if(movieUpdateRqBo.getMoviesInfo().getPrice().intValue() < 0){
            throw new NaviException(FabricResponseCode.BAD_REQUEST, "電影價格不能小於 0。");
        }

        QueryMovieConditionDto moviesConditionDto = new QueryMovieConditionDto();
        moviesConditionDto.setId(movieUpdateRqBo.getId());

        if(!moviesDao.existsById(moviesConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。");
        }
        if(moviesDao.checkStatus(moviesConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此資料已被刪除，無法操作。");
        }

        List<MovieDto> movieDtoList = moviesDao.queryMovies(moviesConditionDto);

        if (CollectionUtils.isEmpty(movieDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "ID: " + movieId + "內容為空。");
        }
        if (movieDtoList.size() > 1) {
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "ID: " + movieId + "超過一筆資料。");
        }

        MovieDto movieDto = movieDtoList.get(0);


        log.info("[{}][update][UpdateMovieInfo Before: {}]", CLASS_NAME, movieDto);
        getUpdateMoviesDto(movieDto, movieUpdateRqBo.getMoviesInfo());
        movieDto = moviesDao.updateMovies(movieDto);
        log.info("[{}][update][UpdateMovieInfo After: {}]", CLASS_NAME, movieDto);


        MovieUpdateRsBo movieUpdateRsBo = new MovieUpdateRsBo();
        movieUpdateRsBo.setMoviesInfoBo(getMoviesInfoBo(movieDto));
        return movieUpdateRsBo;
    }

    private void getUpdateMoviesDto(MovieDto movieDto, MovieUpdateRqBo.MoviesInfoBo moviesInfoBo) {
        movieDto.setMovieName(moviesInfoBo.getMovieName());
        movieDto.setPrice(moviesInfoBo.getPrice());
    }

    private MovieUpdateRsBo.MoviesInfoBo getMoviesInfoBo(MovieDto movieDto){
        MovieUpdateRsBo.MoviesInfoBo movieInfoBo = new MovieUpdateRsBo.MoviesInfoBo();

        movieInfoBo.setMovieName(movieDto.getMovieName());
        movieInfoBo.setPrice(movieDto.getPrice());

        return movieInfoBo;
    }
}
