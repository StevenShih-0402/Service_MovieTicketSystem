package com.ctbcbank.navi.mid.movie.management.service.movieorders.update;

import com.ctbcbank.navi.mid.movie.management.dao.MovieOrdersDao;
import com.ctbcbank.navi.mid.movie.management.dto.*;
import com.ctbcbank.navi.mid.movie.management.repository.MovieOrdersRepository;
import com.ctbcbank.navi.mid.movie.management.repository.MoviesRepository;
import com.ctbcbank.navi.mid.movie.management.repository.UsersRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieOrdersUpdateServiceImpl implements MovieOrdersUpdateService {

    private final String CLASS_NAME = MovieOrdersUpdateServiceImpl.class.getSimpleName();
    private final MovieOrdersDao movieOrdersDao;

    @Override
    public MovieOrderUpdateRsBo update(MovieOrderUpdateRqBo movieOrderUpdateRqBo){

        BigInteger movieOrderId = movieOrderUpdateRqBo.getId();

        QueryMovieOrderConditionDto movieOrdersConditionDto = new QueryMovieOrderConditionDto();
        movieOrdersConditionDto.setId(movieOrderId);

        if(!movieOrdersDao.existsById(movieOrdersConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。");
        }
        if(movieOrdersDao.checkStatus(movieOrderUpdateRqBo.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此資料已被刪除，無法操作。");
        }

        List<MovieOrderNameDto> movieOrderDtoList = movieOrdersDao.queryMovieOrders(movieOrdersConditionDto);

        if (CollectionUtils.isEmpty(movieOrderDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "ID: " + movieOrderId);
        }
        if (movieOrderDtoList.size() > 1) {
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "ID: " + movieOrderId);
        }

        MovieOrderNameDto movieOrderNameDto = movieOrderDtoList.get(0);

        QueryMovieOrderUpdateConditionDto movieOrdersUpdateConditionDto = new QueryMovieOrderUpdateConditionDto();
        movieOrdersUpdateConditionDto.setId(movieOrderId);
        movieOrdersUpdateConditionDto.setQuantity(movieOrderUpdateRqBo.getMovieOrdersInfo().getQuantity());

        log.info("[{}][update][UpdateMovieOrderInfo Before: {}]", CLASS_NAME, movieOrderNameDto);
        movieOrderNameDto = movieOrdersDao.updateMovieOrders(movieOrderNameDto, movieOrdersUpdateConditionDto);
        log.info("[{}][update][UpdateMovieOrderInfo After: {}]", CLASS_NAME, movieOrderNameDto);


        MovieOrderUpdateRsBo movieOrderUpdateRsBo = new MovieOrderUpdateRsBo();
        movieOrderUpdateRsBo.setMovieOrdersInfoBo(getMovieOrdersInfoBo(movieOrderNameDto));
        return movieOrderUpdateRsBo;
    }

    private void getUpdateMovieOrdersDto(MovieOrderDto movieOrderDto, MovieOrderUpdateRqBo.MovieOrdersInfoBo movieOrdersInfoBo) {
        movieOrderDto.setQuantity(movieOrdersInfoBo.getQuantity());
    }

    private MovieOrderUpdateRsBo.MovieOrdersInfoBo getMovieOrdersInfoBo(MovieOrderNameDto movieOrderNameDto){
        MovieOrderUpdateRsBo.MovieOrdersInfoBo movieOrderInfoBo = new MovieOrderUpdateRsBo.MovieOrdersInfoBo();

        movieOrderInfoBo.setQuantity(movieOrderNameDto.getQuantity());
        movieOrderInfoBo.setTotalPrice(movieOrderNameDto.getTotalPrice());

        return movieOrderInfoBo;
    }
}
