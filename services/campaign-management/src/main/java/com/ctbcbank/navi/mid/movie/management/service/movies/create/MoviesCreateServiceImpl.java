package com.ctbcbank.navi.mid.movie.management.service.movies.create;

import com.ctbcbank.navi.mid.movie.management.dao.MoviesDao;
import com.ctbcbank.navi.mid.movie.management.dto.MovieDto;
import com.ctbcbank.navi.mid.movie.management.repository.MoviesRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoviesCreateServiceImpl implements MoviesCreateService {

    private final MoviesDao moviesDao;

    public MovieCreateRsBo create(MovieCreateRqBo movieCreateRqBo){
        MovieDto movieDto = new MovieDto();
        BeanUtils.copyProperties(movieCreateRqBo, movieDto);

        if(moviesDao.existName(movieDto.getMovieName())){
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "電影名稱不能重複。");
        }

        MovieDto saveMovieDto = moviesDao.saveMovies(movieDto);

        System.out.println(saveMovieDto);

        MovieCreateRsBo movieCreateRsBo = new MovieCreateRsBo();
        movieCreateRsBo.setId(saveMovieDto.getId());
        movieCreateRsBo.setMovieName(saveMovieDto.getMovieName());

        return movieCreateRsBo;
    }
}
