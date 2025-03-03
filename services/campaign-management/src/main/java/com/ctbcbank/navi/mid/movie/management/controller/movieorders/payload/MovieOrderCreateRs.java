package com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.checkerframework.checker.index.qual.Positive;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieOrderCreateRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(title = "電影訂單 ID", description = "電影訂單 ID")
    private BigInteger id;

    @NotNull
    @Positive
    @Schema(title = "電影訂單總價", description = "電影訂單總價")
    private BigInteger totalPrice;
}