package com.ctbcbank.navi.mid.movie.management.service.users.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRqBo {

    private BigInteger id;
    private UsersInfoBo usersInfo;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsersInfoBo {
        private String userName;
        private String email;
    }
}