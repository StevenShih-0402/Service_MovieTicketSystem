package com.ctbcbank.navi.mid.campaign.management.vo;

import com.ctbcbank.navi.mid.campaign.management.basicRuleEngine.basic.BasicRuleExecutor;
import com.ctbcbank.navi.mid.campaign.management.enums.RuleNameEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.RuleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleNode {
    private BigInteger ruleSettingId;
    //ruleName and executor are mapping to each other
    private String fieldName;
    private boolean isSync;
    private RuleNameEnum ruleName;
    private BasicRuleExecutor executor;
    private RuleTypeEnum ruleType;
    private String ruleValue;
}
