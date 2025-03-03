package com.ctbcbank.navi.mid.movie.management.service.users.update;

import com.ctbcbank.navi.mid.movie.management.dao.UsersDao;
import com.ctbcbank.navi.mid.movie.management.dto.QueryUserConditionDto;
import com.ctbcbank.navi.mid.movie.management.dto.UserDto;
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
public class UsersUpdateServiceImpl implements UsersUpdateService {

    private final String CLASS_NAME = UsersUpdateServiceImpl.class.getSimpleName();
    private final UsersDao usersDao;

    @Override
    public UserUpdateRsBo update(UserUpdateRqBo userUpdateRqBo){
        BigInteger userId = userUpdateRqBo.getId();

        if(usersDao.existsByEmailExceptId(userUpdateRqBo.getUsersInfo().getEmail(), userUpdateRqBo.getId())){
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "信箱不能重複。");
        }

        QueryUserConditionDto usersConditionDto = new QueryUserConditionDto();
        usersConditionDto.setId(userUpdateRqBo.getId());

        if(!usersDao.existsById(usersConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。");
        }
        if(usersDao.checkStatus(usersConditionDto.getId())){
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "此資料已被刪除，無法操作。");
        }

        List<UserDto> userDtoList = usersDao.queryUsers(usersConditionDto);

        if (CollectionUtils.isEmpty(userDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "ID: " + userId + "沒有回傳資料");
        }
        if (userDtoList.size() > 1) {
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "ID: " + userId + "超過一筆資料");
        }

        UserDto userDto = userDtoList.get(0);

        log.info("[{}][update][UpdateUserInfo Before: {}]", CLASS_NAME, userDto);
        getUpdateUsersDto(userDto, userUpdateRqBo.getUsersInfo());
        userDto = usersDao.updateUsers(userDto);
        log.info("[{}][update][UpdateUserInfo After: {}]", CLASS_NAME, userDto);

        UserUpdateRsBo userUpdateRsBo = new UserUpdateRsBo();
        userUpdateRsBo.setUsersInfoBo(getUsersInfoBo(userDto));
        return userUpdateRsBo;
    }

    private void getUpdateUsersDto(UserDto userDto, UserUpdateRqBo.UsersInfoBo usersInfoBo) {
        userDto.setUserName(usersInfoBo.getUserName());
        userDto.setEmail(usersInfoBo.getEmail());
    }

    private UserUpdateRsBo.UsersInfoBo getUsersInfoBo(UserDto userDto){
        UserUpdateRsBo.UsersInfoBo userInfoBo = new UserUpdateRsBo.UsersInfoBo();

        userInfoBo.setUserName(userDto.getUserName());
        userInfoBo.setEmail(userDto.getEmail());

        return userInfoBo;
    }
}