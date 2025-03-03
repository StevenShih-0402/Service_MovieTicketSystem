package com.ctbcbank.navi.mid.movie.management.service.users.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRqBo {
    private String userName;
    private String email;
}
