package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRuleSettingExtraDto extends BaseDto {
    private BigInteger campaignId;
    private BigInteger ruleGroupId;
    private String ruleName;
    private String ruleValue;
    private String ruleType;
    private String transactionCode;
    private String fieldName;
}
