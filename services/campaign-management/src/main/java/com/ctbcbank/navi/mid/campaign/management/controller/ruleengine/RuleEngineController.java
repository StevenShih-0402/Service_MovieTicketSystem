package com.ctbcbank.navi.mid.campaign.management.controller.ruleengine;

import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineNonSynchronousRq;
import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineNonSynchronousRs;
import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineSynchronousRq;
import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineSynchronousRs;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querynonsynchronous.QueryRuleEngineNonSynchronousConverter;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querynonsynchronous.QueryRuleEngineNonSynchronousRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querynonsynchronous.QueryRuleEngineNonSynchronousRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querynonsynchronous.QueryRuleEngineNonSynchronousService;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querysynchronous.QueryRuleEngineSynchronousConverter;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querysynchronous.QueryRuleEngineSynchronousRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querysynchronous.QueryRuleEngineSynchronousRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querysynchronous.QueryRuleEngineSynchronousService;
import com.ibm.cbmp.fabric.web.api.annotation.PostApiMapping;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/rule-engine/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleEngineController {
    private final QueryRuleEngineNonSynchronousService queryRuleEngineNonSynchronousService;
    private final QueryRuleEngineSynchronousService queryRuleEngineSynchronousService;

    @Operation(summary = "即時取得RuleEngine Tree By 條件(轉帳時間、TransactionCode、IP_NO)", description = "即時取得RuleEngine Tree By 條件(轉帳時間、TransactionCode、IP_NO)")
    @PostApiMapping(value = "query/synchronous")
    QueryRuleEngineSynchronousRs querySynchronous(@Valid @RequestBody QueryRuleEngineSynchronousRq queryRuleEngineSynchronousRq) {
        QueryRuleEngineSynchronousRqBo queryRuleEngineSynchronousRqBo = QueryRuleEngineSynchronousConverter.parseRqToBoRq(queryRuleEngineSynchronousRq);
        QueryRuleEngineSynchronousRsBo queryRuleEngineSynchronousRsBo = queryRuleEngineSynchronousService.query(queryRuleEngineSynchronousRqBo);
        QueryRuleEngineSynchronousRs queryRuleEngineSynchronousRs = QueryRuleEngineSynchronousConverter.parseRsBoToRs(queryRuleEngineSynchronousRsBo);
        return queryRuleEngineSynchronousRs;
    }

    @Operation(summary = "非即時取得RuleEngine Tree By 條件(轉帳時間、TransactionCode、IP_NO)", description = "非即時取得RuleEngine Tree By 條件(轉帳時間、TransactionCode、IP_NO)")
    @PostApiMapping(value = "query/non-synchronous")
    QueryRuleEngineNonSynchronousRs queryNonSynchronous(@Valid @RequestBody QueryRuleEngineNonSynchronousRq queryRuleEngineNonSynchronousRq) {
        QueryRuleEngineNonSynchronousRqBo queryRuleEngineNonSynchronousRqBo = QueryRuleEngineNonSynchronousConverter.parseRqToBoRq(queryRuleEngineNonSynchronousRq);
        QueryRuleEngineNonSynchronousRsBo queryRuleEngineNonSynchronousRsBo = queryRuleEngineNonSynchronousService.query(queryRuleEngineNonSynchronousRqBo);
        QueryRuleEngineNonSynchronousRs queryRuleEngineNonSynchronousRs = QueryRuleEngineNonSynchronousConverter.parseBoRsToRs(queryRuleEngineNonSynchronousRsBo);
        return queryRuleEngineNonSynchronousRs;
    }

}
