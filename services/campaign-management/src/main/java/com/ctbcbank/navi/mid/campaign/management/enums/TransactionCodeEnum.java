package com.ctbcbank.navi.mid.campaign.management.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum TransactionCodeEnum {
    TRANSFER("TRANSFER"),
    EXCHANGE("EXCHANGE");
    private String transactionCode;

    TransactionCodeEnum(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public static Optional<TransactionCodeEnum> fromCode(String code) {
        for (TransactionCodeEnum transactionCode : TransactionCodeEnum.values()) {
            if (transactionCode.getTransactionCode().equals(code)) {
                return Optional.of(transactionCode);
            }
        }
        return Optional.empty();
    }
}
