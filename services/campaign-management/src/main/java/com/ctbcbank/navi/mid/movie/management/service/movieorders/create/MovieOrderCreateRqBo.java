package com.ctbcbank.navi.mid.movie.management.service.movieorders.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieOrderCreateRqBo {
    private BigInteger quantity;
    private BigInteger userId;
    private BigInteger movieId;
}
