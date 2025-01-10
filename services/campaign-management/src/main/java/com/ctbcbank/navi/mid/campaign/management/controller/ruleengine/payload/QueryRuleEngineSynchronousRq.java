package com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRuleEngineSynchronousRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "查詢規則引擎基本資訊", description = "查詢規則引擎基本資訊")
    @JsonProperty("queryRuleEngineBasicInformation")
    private QueryRuleEngineBasicInformation queryRuleEngineBasicInformation;

}
