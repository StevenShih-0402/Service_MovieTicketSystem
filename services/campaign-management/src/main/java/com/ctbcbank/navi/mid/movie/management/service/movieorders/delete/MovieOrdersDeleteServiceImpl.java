package com.ctbcbank.navi.mid.movie.management.service.movieorders.delete;

import com.ctbcbank.navi.mid.movie.management.dao.MovieOrdersDao;
import com.ctbcbank.navi.mid.movie.management.dto.QueryMovieOrderConditionDto;
import com.ctbcbank.navi.mid.movie.management.repository.MovieOrdersRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class MovieOrdersDeleteServiceImpl implements MovieOrdersDeleteService{

    private final MovieOrdersDao movieOrdersDao;

    @Override
    public void delete(MovieOrderDeleteRqBo movieOrderDeleteRqBo){
        QueryMovieOrderConditionDto queryMovieOrderConditionDto = new QueryMovieOrderConditionDto();
        queryMovieOrderConditionDto.setId(movieOrderDeleteRqBo.getId());

        if(!movieOrdersDao.existsById(queryMovieOrderConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。");
        }
        if(movieOrdersDao.checkStatus(queryMovieOrderConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此資料已被刪除，無法操作。");
        }

        movieOrdersDao.deleteMovieOrder(queryMovieOrderConditionDto);
    }
}
