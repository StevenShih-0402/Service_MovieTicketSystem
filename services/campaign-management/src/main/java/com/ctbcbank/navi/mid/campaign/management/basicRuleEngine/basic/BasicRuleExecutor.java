package com.ctbcbank.navi.mid.campaign.management.basicRuleEngine.basic;


import com.ctbcbank.navi.mid.campaign.management.enums.ContextEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.RuleTypeEnum;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
public abstract class BasicRuleExecutor {

    public boolean execute() {
        log.error("Nothing here");
        return false;
    }

//    public boolean execute(Map<ContextEnum, Object> context) {
//        log.error("Nothing here");
//        return false;
//    }

    public boolean execute(Map<ContextEnum, Object> context, RuleTypeEnum ruleType, String ruleValue) {
        log.error("Nothing here");
        return false;
    }
}
