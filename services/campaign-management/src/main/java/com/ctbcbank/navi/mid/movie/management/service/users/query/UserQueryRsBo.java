package com.ctbcbank.navi.mid.movie.management.service.users.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryRsBo {

    private List<UsersInfoBo> usersInfoList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsersInfoBo{
        private String userName;
        private String email;
    }
}
