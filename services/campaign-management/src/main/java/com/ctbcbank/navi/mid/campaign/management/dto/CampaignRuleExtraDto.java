package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRuleExtraDto extends BaseDto {
    private String transactionCode;
    private String ruleName;
    private String ruleDefaultValue;
    private String ruleType;
}
