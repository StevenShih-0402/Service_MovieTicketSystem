package com.ctbcbank.navi.mid.campaign.management.dto;

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
public class QueryCampaignRuleSettingExtraConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transactionCode;
    BigInteger ruleGroupId;
    private List<BigInteger> campaignIdList;
    List<RuleSetting> ruleSettingList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RuleSetting {
        String ruleName;
        String ruleValue;
    }
}
