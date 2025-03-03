package com.ctbcbank.navi.mid.movie.management.service.users.update;

import com.ctbcbank.navi.mid.movie.management.controller.users.payload.UserUpdateRs;
import com.ctbcbank.navi.mid.movie.management.controller.users.payload.UserUpdateRq;

public class UsersUpdateConverter {

    public static UserUpdateRqBo parseToBo(UserUpdateRq userUpdateRq){
        UserUpdateRqBo userUpdateRqBo = new UserUpdateRqBo();

        userUpdateRqBo.setId(userUpdateRq.getId());
        userUpdateRqBo.setUsersInfo(getUsersInfoBo(userUpdateRq.getUsersInfo()));

        return userUpdateRqBo;
    }

    private static UserUpdateRqBo.UsersInfoBo getUsersInfoBo(UserUpdateRq.UsersInfo usersInfo){
        UserUpdateRqBo.UsersInfoBo usersInfoBo = new UserUpdateRqBo.UsersInfoBo();

        usersInfoBo.setUserName(usersInfo.getUserName());
        usersInfoBo.setEmail(usersInfo.getEmail());

        return usersInfoBo;
    }

    public static UserUpdateRs parseToRs(UserUpdateRsBo userUpdateRsBo){
        UserUpdateRs userUpdateRs = new UserUpdateRs();

        userUpdateRs.setUsersInfo(getUsersInfo(userUpdateRsBo.getUsersInfoBo()));

        return userUpdateRs;
    }

    private static UserUpdateRs.UsersInfo getUsersInfo(UserUpdateRsBo.UsersInfoBo usersInfoBo){
        UserUpdateRs.UsersInfo usersInfo = new UserUpdateRs.UsersInfo();

        usersInfo.setUserName(usersInfoBo.getUserName());
        usersInfo.setEmail(usersInfoBo.getEmail());

        return usersInfo;
    }
}