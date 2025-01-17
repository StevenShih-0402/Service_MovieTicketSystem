package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.querybyrule;

import com.ctbcbank.navi.mid.campaign.management.enums.RuleNameEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.RuleTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.TransactionCodeEnum;
import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignQueryByRuleRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Max(value = 1000, message = "size cannot exceed 1000")
    @Min(value = 1, message = "size must be at least 1")
    @Schema(title = "每頁大小", description = "每頁大小", example = "1000")
    private Integer size;

    @NotNull
    @Schema(title = "當前頁數", description = "當前頁數,0為第一頁...以此類推", example = "0")
    private Integer number;

    @Schema(title = "活動名稱", description = "活動名稱")
    private String campaignName;

    @Schema(title = "規則清單", description = "規則清單")
    private List<Rule> ruleList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rule {

        @NotNull
        @Schema(title = "交易代碼", description = "交易代碼", example = "TRANSFER")
        private TransactionCodeEnum transactionCode;

        @Schema(title = "規則名稱", description = "規則名稱", example = "TIMES_COUNT_RULE")
        private RuleNameEnum ruleName;

        @Schema(title = "規則類型", description = "規則類型", example = "EQUAL")
        private RuleTypeEnum ruleType;

        @Schema(title = "規則設定值", description = "規則設定值", example = "")
        private String ruleValue;
    }
}
