package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRuleRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "規則清單", description = "規則清單")
    @JsonProperty("ruleList")
    List<Rule> ruleList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rule {
        @Schema(title = "規則唯一碼", description = "規則唯一碼")
        @JsonProperty("ruleId")
        BigInteger ruleId;

        @Schema(title = "交易代碼", description = "交易代碼")
        @JsonProperty("transactionCode")
        String transactionCode;

        @Schema(title = "規則名稱", description = "規則名稱")
        @JsonProperty("ruleName")
        String ruleName;

        @Schema(title = "規則類型", description = "規則類型")
        @JsonProperty("ruleType")
        String ruleType;

        @Schema(title = "規則值", description = "規則值")
        @JsonProperty("ruleDefaultValue")
        String ruleDefaultValue;
    }
}
