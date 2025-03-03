package com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieOrderCreateRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    @Schema(title = "電影訂單數量", description = "電影訂單數量")
    private BigInteger quantity;

    @NotNull
    @Schema(title = "用戶編號", description = "用戶編號")
    private BigInteger userId;

    @NotNull
    @Schema(title = "電影編號", description = "電影編號")
    private BigInteger movieId;
}
