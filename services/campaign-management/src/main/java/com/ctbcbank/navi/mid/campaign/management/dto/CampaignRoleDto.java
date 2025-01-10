package com.ctbcbank.navi.mid.campaign.management.dto;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignRoleStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignRoleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRoleDto extends BaseDto {
    private String name;
    private String description;
    private CampaignRoleStatusEnum status;
    private CampaignRoleTypeEnum type;
}
