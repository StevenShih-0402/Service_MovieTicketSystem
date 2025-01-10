package com.ctbcbank.navi.mid.campaign.management.service.permission.querybyuser;

import com.ctbcbank.navi.mid.campaign.management.controller.permission.payload.PermissionQueryByUserRs;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignRoleStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignRoleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionQueryByUserRsBo {

    private List<RoleBo> roleList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleBo {
        private BigInteger id;
        private String name;
        private String description;
        private CampaignRoleStatusEnum status;
        private CampaignRoleTypeEnum type;
        private List<PermissionBo> permissionList;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PermissionBo {
        private BigInteger id;
        private String code;
        private String name;
        private String description;
    }
}
