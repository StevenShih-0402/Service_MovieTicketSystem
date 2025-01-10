package com.ctbcbank.navi.mid.campaign.management.service.campaign;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.QueryRuleRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.QueryRuleRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class QueryCampaignRuleConverter {
    public static QueryRuleRqBo parseToBo(QueryRuleRq queryRuleRq) {
        QueryRuleRqBo queryRuleRqBo = new QueryRuleRqBo();
        queryRuleRqBo.setTransactionCode(queryRuleRq.getTransactionCode());
        return queryRuleRqBo;
    }

    public static QueryRuleRs parseToRs(QueryRuleRsBo queryRuleRsBo) {
        QueryRuleRs queryRuleRs = new QueryRuleRs();
        queryRuleRs.setRuleList(getRuleList(queryRuleRsBo.getRuleList()));
        return queryRuleRs;
    }

    private static List<QueryRuleRs.Rule> getRuleList(List<QueryRuleRsBo.RuleBo> ruleBoList) {
        List<QueryRuleRs.Rule> ruleList = new ArrayList<>();
        if (CollectionUtils.isEmpty(ruleBoList)) {
            return ruleList;
        }
        ruleList = ruleBoList.stream().map(x -> {
            QueryRuleRs.Rule rule = new QueryRuleRs.Rule();
            rule.setRuleId(x.getRuleId());
            rule.setTransactionCode(x.getTransactionCode());
            rule.setRuleName(x.getRuleName());
            rule.setRuleDefaultValue(x.getRuleDefaultValue());
            rule.setRuleType(x.getRuleType());
            return rule;
        }).toList();
        return ruleList;
    }

}
