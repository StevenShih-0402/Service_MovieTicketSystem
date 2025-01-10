package com.ctbcbank.navi.mid.campaign.management.service.campaign;

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
public class QueryRuleRsBo {
    List<RuleBo> ruleList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RuleBo {
        BigInteger ruleId;
        String transactionCode;
        String ruleName;
        String ruleType;
        String ruleDefaultValue;
    }
}
