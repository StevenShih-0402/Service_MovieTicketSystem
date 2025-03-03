package com.ctbcbank.navi.mid.movie.management.controller.movies.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCreateRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(title = "電影 ID", description = "電影 ID")
    private BigInteger id;

    @NotBlank
    @Schema(title = "電影名稱", description = "電影名稱")
    private String movieName;
}
