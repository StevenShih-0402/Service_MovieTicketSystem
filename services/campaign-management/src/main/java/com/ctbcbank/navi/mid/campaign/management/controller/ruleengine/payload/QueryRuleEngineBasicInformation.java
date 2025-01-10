package com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload;

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
public class QueryRuleEngineBasicInformation extends QueryRuleEngineDynamicField{

    @Schema(title = "關係人號碼", description = "關係人號碼", example = "1", maxLength = 38)
    @JsonProperty("ipNo")
    private BigInteger ipNo;

    @Schema(title = "交易代碼", description = "交易代碼", example = "TRANSFER")
    @JsonProperty("transactionCode")
    private String transactionCode;

    @Schema(title = "轉帳日期", description = "轉帳日期", example = "2024-01-01")
    @JsonProperty("tranDateTime")
    private LocalDateTime tranDateTime;

    @Schema(title = "entityType", description = "entityType")
    @JsonProperty("entityType")
    private String entityType;

    @Schema(title = "entityReferenceNumber", description = "entityReferenceNumber")
    @JsonProperty("entityReferenceNumber")
    private String entityReferenceNumber;

    @Schema(title = "increment", description = "increment")
    @JsonProperty("increment")
    private String increment;

    @Schema(title = "轉帳資訊物件", description = "轉帳資訊物件")
    @JsonProperty("transferRq")
    private TransferRq transferRq;

    @Schema(title = "換匯資訊物件", description = "換匯資訊物件")
    @JsonProperty("exchangeRq")
    private ExchangeRq exchangeRq;

    @Data
    @SuperBuilder
    public static class TransferRq {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangeRq {
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
