package com.ctbcbank.navi.mid.movie.management.service.users.delete;

import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieDeleteRq;
import com.ctbcbank.navi.mid.movie.management.controller.users.payload.UserDeleteRq;
import com.ctbcbank.navi.mid.movie.management.service.movies.delete.MovieDeleteRqBo;

public class UsersDeleteConverter {
    public static UserDeleteRqBo parseToBo(UserDeleteRq usersDeleteRq){
        UserDeleteRqBo userDeleteRqBo = new UserDeleteRqBo();

        userDeleteRqBo.setId(usersDeleteRq.getId());

        return userDeleteRqBo;
    }
}
