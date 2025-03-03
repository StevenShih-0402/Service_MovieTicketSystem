package com.ctbcbank.navi.mid.movie.management.service.users.delete;

import com.ctbcbank.navi.mid.movie.management.dao.UsersDao;
import com.ctbcbank.navi.mid.movie.management.dto.QueryMovieConditionDto;
import com.ctbcbank.navi.mid.movie.management.dto.QueryUserConditionDto;
import com.ctbcbank.navi.mid.movie.management.repository.UsersRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class UsersDeleteServiceImpl implements UsersDeleteService{

    private final UsersDao usersDao;

    @Override
    public void delete(UserDeleteRqBo userDeleteRqBo){
        QueryUserConditionDto queryUserConditionDto = new QueryUserConditionDto();
        queryUserConditionDto.setId(userDeleteRqBo.getId());

        if(!usersDao.existsById(queryUserConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。");
        }
        if(usersDao.checkStatus(queryUserConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此資料已被刪除，無法操作。");
        }

        usersDao.deleteUser(queryUserConditionDto);
    }
}
