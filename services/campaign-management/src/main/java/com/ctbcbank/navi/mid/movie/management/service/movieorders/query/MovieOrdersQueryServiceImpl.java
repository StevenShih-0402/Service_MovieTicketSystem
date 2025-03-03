package com.ctbcbank.navi.mid.movie.management.service.movieorders.query;

import com.ctbcbank.navi.mid.movie.management.dao.MovieOrdersDao;
import com.ctbcbank.navi.mid.movie.management.dao.MoviesDao;
import com.ctbcbank.navi.mid.movie.management.dto.*;
import com.ctbcbank.navi.mid.movie.management.repository.MovieOrdersRepository;
import com.ctbcbank.navi.mid.movie.management.service.movies.query.MovieQueryRqBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.query.MovieQueryRsBo;
import com.ctbcbank.navi.mid.movie.management.service.movies.query.MoviesQueryServiceImpl;
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
public class MovieOrdersQueryServiceImpl implements MovieOrdersQueryService {

    private final String CLASS_NAME = MovieOrdersQueryServiceImpl.class.getSimpleName();
    private final MovieOrdersDao movieOrdersDao;

    @Override
    @Transactional
    public MovieOrderQueryRsBo query(MovieOrderQueryRqBo movieOrderQueryRqBo){
        QueryMovieOrderConditionDto movieOrdersConditionDto = new QueryMovieOrderConditionDto();
        movieOrdersConditionDto.setId(movieOrderQueryRqBo.getId());

        if(!movieOrdersDao.existsById(movieOrdersConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。");
        }
        if(movieOrdersDao.checkStatus(movieOrdersConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此資料已被刪除，無法操作。");
        }

        List<MovieOrderNameDto> movieOrderDtoList = movieOrdersDao.queryMovieOrders(movieOrdersConditionDto);

        log.info("[{}][query][movieOrderDtoList.size: {}]", CLASS_NAME, movieOrderDtoList.size());

        List<MovieOrderQueryRsBo.MovieOrderInfoBo> movieOrdersBoList = new ArrayList<>();
        movieOrderDtoList.forEach(x ->{
            MovieOrderQueryRsBo.MovieOrderInfoBo movieOrdersBo = MovieOrderQueryRsBo.MovieOrderInfoBo.builder()
                    .movieName(x.getMovieName())
                    .userName(x.getUserName())
                    .quantity(x.getQuantity())
                    .totalPrice(x.getTotalPrice())
                    .build();
            movieOrdersBoList.add(movieOrdersBo);
        });
        MovieOrderQueryRsBo movieOrderQueryRsBo = new MovieOrderQueryRsBo();
        movieOrderQueryRsBo.setMovieOrderInfoBo(movieOrdersBoList);
        return movieOrderQueryRsBo;
    }
}
