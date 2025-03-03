package com.ctbcbank.navi.mid.movie.management.service.movieorders.create;

import com.ctbcbank.navi.mid.movie.management.dao.MovieOrdersDao;
import com.ctbcbank.navi.mid.movie.management.dao.MoviesDao;
import com.ctbcbank.navi.mid.movie.management.dao.UsersDao;
import com.ctbcbank.navi.mid.movie.management.dto.MovieOrderDto;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieOrdersCreateServiceImpl implements MovieOrdersCreateService{

    private final MovieOrdersDao movieOrdersDao;
    private final MoviesDao moviesDao;
    private final UsersDao usersDao;

    @Override
    public MovieOrderCreateRsBo create(MovieOrderCreateRqBo movieOrderCreateRqBo){
        MovieOrderDto movieOrderDto = new MovieOrderDto();

        if(!moviesDao.existsById(movieOrderCreateRqBo.getMovieId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此電影不存在。");
        }
        if(!usersDao.existsById(movieOrderCreateRqBo.getUserId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此用戶不存在。");
        }

        if(moviesDao.checkStatus(movieOrderCreateRqBo.getMovieId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此電影已被刪除。");
        }
        if(usersDao.checkStatus(movieOrderCreateRqBo.getUserId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此用戶已被刪除。");
        }
        BeanUtils.copyProperties(movieOrderCreateRqBo, movieOrderDto);

        MovieOrderDto saveMovieOrderDto = movieOrdersDao.saveMovieOrders(movieOrderDto);

        MovieOrderCreateRsBo movieOrderCreateRsBo = new MovieOrderCreateRsBo();
        movieOrderCreateRsBo.setId(saveMovieOrderDto.getId());
        movieOrderCreateRsBo.setTotalPrice(saveMovieOrderDto.getTotalPrice());

        return movieOrderCreateRsBo;
    }
}
