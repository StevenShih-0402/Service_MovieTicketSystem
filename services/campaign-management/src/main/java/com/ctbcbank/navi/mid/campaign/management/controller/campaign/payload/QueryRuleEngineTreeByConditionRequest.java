package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload;

import com.ctbcbank.navi.mid.campaign.management.enums.TransactionCodeEnum;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.QueryRuleEngineTreeByConditionRequestBo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRuleEngineTreeByConditionRequest extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, String> map;

    @Schema(title = "轉帳日期", description = "轉帳日期", example = "2024-01-01")
    @JsonProperty("transactionDateTime")
    @NotBlank
    private LocalDateTime transactionDateTime;

    @Schema(title = "規則動作", description = "規則動作", example = "TRANSFER")
    @JsonProperty("transactionCode")
    private String transactionCode;

    @Schema(title = "關係人號碼", description = "關係人號碼", example = "1", maxLength = 38)
    @JsonProperty("involvedPartyNo")
    private BigInteger involvedPartyNo;

    @Schema(title = "是否立即", description = "是否立即", example = "true")
    @JsonProperty("isSynchronous")
    private boolean isSynchronous;

    @Schema(title = "轉帳", description = "轉帳")
    @JsonProperty("transferRq")
    private TransferRq transferRq;

    @Schema(title = "換匯", description = "換匯")
    @JsonProperty("ExchangeRq")
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

    public static Optional<QueryRuleEngineTreeByConditionRequestBo> parstToBo(QueryRuleEngineTreeByConditionRequest queryRuleEngineTreeByConditionRequest) {
        if (queryRuleEngineTreeByConditionRequest == null) {
            return Optional.empty();
        }
        QueryRuleEngineTreeByConditionRequestBo queryRuleEngineTreeByConditionRequestBo = new QueryRuleEngineTreeByConditionRequestBo();
        queryRuleEngineTreeByConditionRequestBo.setTransactionDateTime(queryRuleEngineTreeByConditionRequest.getTransactionDateTime());
        queryRuleEngineTreeByConditionRequestBo.setTransactionCode(queryRuleEngineTreeByConditionRequest.getTransactionCode());
        queryRuleEngineTreeByConditionRequestBo.setInvolvedPartyNo(queryRuleEngineTreeByConditionRequest.getInvolvedPartyNo());
        queryRuleEngineTreeByConditionRequestBo.setSynchronous(queryRuleEngineTreeByConditionRequest.isSynchronous());


        Optional<TransactionCodeEnum> transactionCodeEnumOptional = TransactionCodeEnum.fromCode(queryRuleEngineTreeByConditionRequest.getTransactionCode());
        if (transactionCodeEnumOptional.isPresent()) {
            TransactionCodeEnum transactionCodeEnum = transactionCodeEnumOptional.get();
            switch (transactionCodeEnum) {
                case TRANSFER -> {

                }
                case EXCHANGE -> {
                    if (ObjectUtils.isEmpty(queryRuleEngineTreeByConditionRequest.getExchangeRq())) {
                        throw new NaviException(FabricResponseCode.INVALID_DATA);
                    }
                    ExchangeRq exchangeRq = queryRuleEngineTreeByConditionRequest.getExchangeRq();
                    QueryRuleEngineTreeByConditionRequestBo.ExchangeRqBo exchangeRqBo = new QueryRuleEngineTreeByConditionRequestBo.ExchangeRqBo();
                    exchangeRqBo.setFromCurrency(exchangeRq.getFromCurrency());
                    exchangeRqBo.setToCurrency(exchangeRq.getToCurrency());
                    exchangeRqBo.setSourceSystem(exchangeRq.getSourceSystem());
                    exchangeRqBo.setBranch(exchangeRq.getBranch());
                    queryRuleEngineTreeByConditionRequestBo.setExchangeRq(exchangeRqBo);
                }

            }
        }
        return Optional.of(queryRuleEngineTreeByConditionRequestBo);
    }

}
