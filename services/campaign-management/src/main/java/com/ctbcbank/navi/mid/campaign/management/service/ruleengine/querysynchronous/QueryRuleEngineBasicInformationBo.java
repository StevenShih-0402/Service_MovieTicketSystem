package com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querysynchronous;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRuleEngineBasicInformationBo extends QueryRuleEngineDynamicFieldBo {

    @Schema(title = "關係人號碼", description = "關係人號碼", example = "1", maxLength = 38)
    @JsonProperty("ipNo")
    private BigInteger ipNo;

    @Schema(title = "交易代碼", description = "交易代碼", example = "TRANSFER")
    @JsonProperty("transactionCode")
    private String transactionCode;

    @Schema(title = "轉帳日期", description = "轉帳日期", example = "2024-01-01")
    @JsonProperty("tranDateTime")
    private LocalDateTime tranDateTime;

    @JsonProperty("entityType")
    private String entityType;

    @JsonProperty("entityReferenceNumber")
    private String entityReferenceNumber;

    @JsonProperty("increment")
    private String increment;

    @JsonProperty("transferRq")
    private TransferRqBo transferRq;

    @JsonProperty("exchangeRq")
    private ExchangeRqBo exchangeRq;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    public static class TransferRqBo {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangeRqBo {
        @Schema(title = "原幣別", description = "原幣別", example = "TWD")
        @JsonProperty("fromCurrency")
        private String fromCurrency;

        @Schema(title = "兌換幣別", description = "兌換幣別", example = "USD")
        @JsonProperty("toCurrency")
        private String toCurrency;

        @Schema(title = "系統來源", description = "系統來源", example = "CABINET")
        @JsonProperty("sourceSystem")
        private String sourceSystem;

        @Schema(title = "分行", description = "分行", example = "DAAN")
        @JsonProperty("branch")
        private String branch;
    }

}
