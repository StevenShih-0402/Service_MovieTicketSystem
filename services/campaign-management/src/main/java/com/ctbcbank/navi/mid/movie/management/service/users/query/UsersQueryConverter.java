package com.ctbcbank.navi.mid.movie.management.service.users.query;

import com.ctbcbank.navi.mid.movie.management.controller.users.payload.UserQueryRq;
import com.ctbcbank.navi.mid.movie.management.controller.users.payload.UserQueryRs;

import java.util.List;

public class UsersQueryConverter {
    public static UserQueryRqBo parseToBo(UserQueryRq userQueryRq){
        UserQueryRqBo userQueryRqBo = new UserQueryRqBo();

        userQueryRqBo.setId(userQueryRq.getId());

        return userQueryRqBo;
    }

    public static UserQueryRs parseToRs(UserQueryRsBo userQueryRsBo){
        UserQueryRs userQueryRs = new UserQueryRs();

        userQueryRs.setUsersInfoList(getUsersInfoList(userQueryRsBo.getUsersInfoList()));

        return userQueryRs;
    }

    private static List<UserQueryRs.UsersInfo> getUsersInfoList(List<UserQueryRsBo.UsersInfoBo> usersInfoBoList){
        List<UserQueryRs.UsersInfo> usersInfoList = usersInfoBoList.stream().map(x ->{
            UserQueryRs.UsersInfo usersInfo = new UserQueryRs.UsersInfo();
            usersInfo.setUserName(x.getUserName());
            usersInfo.setEmail(x.getEmail());

            return usersInfo;
        }).toList();

        return usersInfoList;
    }
}
