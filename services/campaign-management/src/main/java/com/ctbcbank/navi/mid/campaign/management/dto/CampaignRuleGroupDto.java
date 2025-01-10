package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRuleGroupDto extends BaseDto {
    private BigInteger campaignId;
    private BigInteger ruleGroupParentId;
    private String hasCoupon;
    private Long matchCount;
}
