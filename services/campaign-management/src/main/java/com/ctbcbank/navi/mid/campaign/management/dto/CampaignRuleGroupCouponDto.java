package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRuleGroupCouponDto extends BaseDto {
    private BigInteger campaignId;
    private BigInteger ruleGroupId;
    private String couponTemplateNo;
}
