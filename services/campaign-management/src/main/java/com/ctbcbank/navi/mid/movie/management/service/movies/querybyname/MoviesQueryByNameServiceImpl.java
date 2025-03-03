package com.ctbcbank.navi.mid.movie.management.service.movies.querybyname;

import com.ctbcbank.navi.mid.movie.management.dao.MoviesDao;
import com.ctbcbank.navi.mid.movie.management.dto.MovieDto;
import com.ctbcbank.navi.mid.movie.management.dto.MovieNameDto;
import com.ctbcbank.navi.mid.movie.management.dto.QueryMovieConditionDto;
import com.ctbcbank.navi.mid.movie.management.repository.MoviesRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class MoviesQueryByNameServiceImpl implements MoviesQueryByNameService {

    private final String CLASS_NAME = MoviesQueryByNameServiceImpl.class.getSimpleName();
    private final MoviesDao moviesDao;

    @Override
    public MovieQueryByNameRsBo queryByName(MovieQueryByNameRqBo movieQueryByNameRqBo){

        MovieNameDto movieNameDto = new MovieNameDto();
        movieNameDto.setKeyWord(movieQueryByNameRqBo.getKeyWord());
        movieNameDto.setPrice(movieQueryByNameRqBo.getPrice());
        movieNameDto.setPageNo(BigInteger.valueOf(movieQueryByNameRqBo.getPageNo().intValue() - 1));

        if(movieNameDto.getPrice().intValue() < 400 && !(movieNameDto.getKeyWord().matches("[0-9]+"))){
            throw new NaviException(FabricResponseCode.INVALID_DATA, "電影價格小於400時，電影關鍵字只能輸入數字。");
        }
        if(movieNameDto.getPrice().intValue() >= 400 && !(movieNameDto.getKeyWord().matches("[A-Za-z]+"))){
            throw new NaviException(FabricResponseCode.INVALID_DATA, "電影價格大於等於400時，電影關鍵字只能輸入字母。");
        }

        List<MovieDto> movieDtoList = moviesDao.queryMoviesByName(movieNameDto);

        log.info("[{}][query][movieDtoList.size: {}]", CLASS_NAME, movieDtoList.size());

        if(movieDtoList.isEmpty()){
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "找不到資料。");
        }

        List<MovieQueryByNameRsBo.MoviesInfoBo> moviesBoList = new ArrayList<>();
        movieDtoList.forEach(x ->{
            MovieQueryByNameRsBo.MoviesInfoBo moviesBo = MovieQueryByNameRsBo.MoviesInfoBo.builder()
                    .movieName(x.getMovieName())
                    .price(x.getPrice())
                    .build();
            moviesBoList.add(moviesBo);
        });
        MovieQueryByNameRsBo movieQueryByNameRsBo = new MovieQueryByNameRsBo();
        movieQueryByNameRsBo.setMoviesInfoList(moviesBoList);
        return movieQueryByNameRsBo;
    }
}
