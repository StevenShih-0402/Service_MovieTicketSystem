package com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload;

import com.ctbcbank.navi.mid.campaign.management.vo.RuleEngine;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class QueryRuleEngineSynchronousRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "規則樹", description = "規則樹")
    private Map<BigInteger, RuleEngine> ruleEngineTree;

}
