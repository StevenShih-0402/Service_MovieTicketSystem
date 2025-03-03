package com.ctbcbank.navi.mid.movie.management.service.users.create;

import com.ctbcbank.navi.mid.movie.management.dao.UsersDao;
import com.ctbcbank.navi.mid.movie.management.dto.UserDto;
import com.ctbcbank.navi.mid.movie.management.repository.UsersRepository;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersCreateServiceImpl implements UsersCreateService {

    private final UsersDao usersDao;

    public UserCreateRsBo create(UserCreateRqBo userCreateRqBo){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userCreateRqBo, userDto);

        if(usersDao.existsByEmail(userDto.getEmail())){
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "信箱不能重複。");
        }

        UserDto saveUserDto = usersDao.saveUsers(userDto);

        UserCreateRsBo userCreateRsBo = new UserCreateRsBo();
        userCreateRsBo.setId(saveUserDto.getId());
        userCreateRsBo.setUserName(saveUserDto.getUserName());

        return userCreateRsBo;
    }
}
