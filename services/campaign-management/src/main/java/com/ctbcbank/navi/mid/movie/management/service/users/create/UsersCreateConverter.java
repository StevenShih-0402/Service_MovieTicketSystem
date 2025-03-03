package com.ctbcbank.navi.mid.movie.management.service.users.create;

import com.ctbcbank.navi.mid.movie.management.controller.users.payload.UserCreateRq;
import com.ctbcbank.navi.mid.movie.management.controller.users.payload.UserCreateRs;

public class UsersCreateConverter {
    public static UserCreateRqBo parseToBo(UserCreateRq usersCreateRq){
        UserCreateRqBo userCreateRqBo = new UserCreateRqBo();

        userCreateRqBo.setUserName(usersCreateRq.getUserName());
        userCreateRqBo.setEmail(usersCreateRq.getEmail());

        return userCreateRqBo;
    }

    public static UserCreateRs parseToRs(UserCreateRsBo userCreateRsBo){
        UserCreateRs usersCreateRs = new UserCreateRs();

        usersCreateRs.setId(userCreateRsBo.getId());
        usersCreateRs.setUserName(userCreateRsBo.getUserName());

        return usersCreateRs;
    }
}