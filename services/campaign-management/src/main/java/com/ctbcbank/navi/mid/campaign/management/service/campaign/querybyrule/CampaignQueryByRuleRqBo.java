package com.ctbcbank.navi.mid.campaign.management.service.campaign.querybyrule;

import com.ctbcbank.navi.mid.campaign.management.enums.RuleNameEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.RuleTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.TransactionCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignQueryByRuleRqBo {
    private Integer size;
    private Integer number;
    private String campaignName;
    private List<RuleBo> ruleList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RuleBo {
        private TransactionCodeEnum transactionCode;
        private RuleNameEnum ruleName;
        private RuleTypeEnum ruleType;
        private String ruleValue;
    }
}
