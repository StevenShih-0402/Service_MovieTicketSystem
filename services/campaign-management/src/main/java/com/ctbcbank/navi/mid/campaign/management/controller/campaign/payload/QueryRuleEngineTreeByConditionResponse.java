package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload;

import com.ctbcbank.navi.mid.campaign.management.service.campaign.QueryRuleEngineTreeByConditionResponseBo;
import com.ctbcbank.navi.mid.campaign.management.vo.RuleEngine;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRuleEngineTreeByConditionResponse extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<BigInteger, RuleEngine> ruleEngineTree;

    public static QueryRuleEngineTreeByConditionResponse parseToResponse(QueryRuleEngineTreeByConditionResponseBo queryRuleEngineTreeByConditionResponseBo) {
        QueryRuleEngineTreeByConditionResponse queryRuleEngineTreeByConditionResponse = new QueryRuleEngineTreeByConditionResponse();
        queryRuleEngineTreeByConditionResponse.setRuleEngineTree(queryRuleEngineTreeByConditionResponseBo.getRuleEngineTree());
        return queryRuleEngineTreeByConditionResponse;
    }
}
