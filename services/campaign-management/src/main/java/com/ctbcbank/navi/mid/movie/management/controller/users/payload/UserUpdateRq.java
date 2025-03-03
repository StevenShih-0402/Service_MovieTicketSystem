package com.ctbcbank.navi.mid.movie.management.controller.users.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(title = "ID", description = "ID")
    private BigInteger id;

    @Valid
    @Schema(title = "用戶資訊", description = "用戶資訊")
    private UsersInfo usersInfo;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsersInfo {
        @NotBlank
        @Length(max = 50)
        @Schema(title = "用戶名稱", description = "用戶名稱")
        private String userName;

        @NotBlank
        @Email
        @Schema(title = "用戶信箱", description = "用戶信箱")
        private String email;
    }
}
