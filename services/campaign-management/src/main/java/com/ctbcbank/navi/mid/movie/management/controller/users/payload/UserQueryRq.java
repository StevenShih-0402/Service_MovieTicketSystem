package com.ctbcbank.navi.mid.movie.management.controller.users.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class UserQueryRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(title = "ID", description = "ID")
    private BigInteger id;
}
