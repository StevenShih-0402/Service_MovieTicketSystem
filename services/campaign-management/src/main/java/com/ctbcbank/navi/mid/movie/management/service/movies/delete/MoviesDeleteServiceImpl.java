package com.ctbcbank.navi.mid.movie.management.service.movies.delete;

import com.ctbcbank.navi.mid.movie.management.dao.MoviesDao;
import com.ctbcbank.navi.mid.movie.management.dto.QueryMovieConditionDto;
import com.ctbcbank.navi.mid.movie.management.repository.MoviesRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class MoviesDeleteServiceImpl implements MoviesDeleteService{

    private final MoviesDao moviesDao;

    @Override
    public void delete(MovieDeleteRqBo movieDeleteRqBo){
        QueryMovieConditionDto queryMovieConditionDto = new QueryMovieConditionDto();
        queryMovieConditionDto.setId(movieDeleteRqBo.getId());

        if(!moviesDao.existsById(queryMovieConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。");
        }
        if(moviesDao.checkStatus(queryMovieConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此資料已被刪除，無法操作。");
        }

        moviesDao.deleteMovie(queryMovieConditionDto);
    }
}