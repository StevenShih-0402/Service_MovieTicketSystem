package com.ctbcbank.navi.mid.campaign.management.controller.permission.payload;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignRoleStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignRoleTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionQueryByUserRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "角色清單", description = "角色清單")
    private List<Role> roleList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Role {

        @Schema(title = "角色編號", description = "角色編號", example = "1")
        private BigInteger id;

        @Schema(title = "角色名稱", description = "角色名稱", example = "經辦")
        private String name;

        @Schema(title = "角色描述", description = "角色描述", example = "經辦")
        private String description;

        @Schema(title = "角色狀態", description = "角色狀態", example = "ACTIVATE")
        private CampaignRoleStatusEnum status;

        @Schema(title = "角色類型", description = "角色類型", example = "GENERAL")
        private CampaignRoleTypeEnum type;

        @Schema(title = "權限清單", description = "權限清單")
        private List<Permission> permissionList;
    }
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Permission {
        @Schema(title = "權限編號", description = "權限編號", example = "1")
        private BigInteger id;

        @Schema(title = "權限代碼", description = "權限代碼", example = "FORM_VIEW")
        private String code;

        @Schema(title = "權限名稱", description = "權限名稱", example = "申請單檢視")
        private String name;

        @Schema(title = "權限描述", description = "權限描述", example = "申請單檢視")
        private String description;

    }
}
