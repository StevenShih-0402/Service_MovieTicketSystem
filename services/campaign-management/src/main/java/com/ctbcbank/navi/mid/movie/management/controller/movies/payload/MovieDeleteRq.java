package com.ctbcbank.navi.mid.movie.management.controller.movies.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDeleteRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Positive
    @NotNull
    @Schema(title = "ID", description = "ID")
    private BigInteger id;
}
