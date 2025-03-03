package com.ctbcbank.navi.mid.movie.management.service.users.query;

import com.ctbcbank.navi.mid.movie.management.dao.UsersDao;
import com.ctbcbank.navi.mid.movie.management.dto.QueryUserConditionDto;
import com.ctbcbank.navi.mid.movie.management.dto.UserDto;
import com.ctbcbank.navi.mid.movie.management.repository.UsersRepository;
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
public class UsersQueryServiceImpl implements UsersQueryService {

    private final String CLASS_NAME = UsersQueryServiceImpl.class.getSimpleName();
    private final UsersDao usersDao;

    @Override
    @Transactional
    public UserQueryRsBo query(UserQueryRqBo userQueryRqBo){
        QueryUserConditionDto usersConditionDto = new QueryUserConditionDto();
        usersConditionDto.setId(userQueryRqBo.getId());

        if(!usersDao.existsById(usersConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。");
        }
        if(usersDao.checkStatus(usersConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此資料已被刪除，無法操作。");
        }

        List<UserDto> userDtoList = usersDao.queryUsers(usersConditionDto);

        log.info("[{}][query][userDtoList.size: {}]", CLASS_NAME, userDtoList.size());

        List<UserQueryRsBo.UsersInfoBo> usersBoList = new ArrayList<>();
        userDtoList.forEach(x ->{
            UserQueryRsBo.UsersInfoBo usersBo = UserQueryRsBo.UsersInfoBo.builder()
                    .userName(x.getUserName())
                    .email(x.getEmail())
                    .build();
            usersBoList.add(usersBo);
        });
        UserQueryRsBo userQueryRsBo = new UserQueryRsBo();
        userQueryRsBo.setUsersInfoList(usersBoList);
        return userQueryRsBo;
    }
}
