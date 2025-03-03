package com.ctbcbank.navi.mid.movie.management.controller.users.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "用戶資訊", description = "用戶資訊")
    private UsersInfo usersInfo;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsersInfo {
        @NotBlank
        @NotNull
        @Schema(title = "用戶名稱", description = "用戶名稱")
        private String userName;

        @NotBlank
        @NotNull
        @Email
        @Schema(title = "用戶信箱", description = "用戶信箱")
        private String email;
    }
}
