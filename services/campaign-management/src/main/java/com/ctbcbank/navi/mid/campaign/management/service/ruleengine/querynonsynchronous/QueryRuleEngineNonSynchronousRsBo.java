package com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querynonsynchronous;

import com.ctbcbank.navi.mid.campaign.management.vo.RuleEngine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRuleEngineNonSynchronousRsBo {
    private Map<BigInteger, RuleEngine> ruleEngineTree;
}
