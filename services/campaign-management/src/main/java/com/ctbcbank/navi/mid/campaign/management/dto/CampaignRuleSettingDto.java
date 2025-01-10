package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRuleSettingDto extends BaseDto{
    private BigInteger campaignId;
    private BigInteger ruleGroupId;
    private String transactionCode;
    private String fieldName;
    private String ruleName;
    private String ruleValue;
    private String ruleType;
}
