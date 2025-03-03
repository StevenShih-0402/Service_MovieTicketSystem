package com.ctbcbank.navi.mid.movie.management.service.users.delete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeleteRqBo {
    private BigInteger id;
}
