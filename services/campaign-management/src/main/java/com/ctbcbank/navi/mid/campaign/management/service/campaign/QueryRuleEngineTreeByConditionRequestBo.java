package com.ctbcbank.navi.mid.campaign.management.service.campaign;

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
public class QueryRuleEngineTreeByConditionRequestBo {

    private LocalDateTime transactionDateTime;

    private String transactionCode;

    private BigInteger involvedPartyNo;

    private boolean isSynchronous;

    private TransferRqBo transferRq;

    private ExchangeRqBo exchangeRq;

    @Data
    @SuperBuilder
    public static class TransferRqBo{

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangeRqBo{
        private String fromCurrency;
        private String toCurrency;
        private String sourceSystem;
        private String branch;
    }
}
