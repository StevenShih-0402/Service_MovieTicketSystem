package com.ctbcbank.navi.mid.campaign.management.enums;

import lombok.Getter;

@Getter
public enum TransactionCodeEnum {
    TRANSFER("TRANSFER"),
    EXCHANGE("EXCHANGE"),
    BATCH("BATCH");
    private String transactionCode;

    TransactionCodeEnum(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public static TransactionCodeEnum fromCode(String code) {
        for (TransactionCodeEnum transactionCode : TransactionCodeEnum.values()) {
            if (transactionCode.getTransactionCode().equals(code)) {
                return transactionCode;
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
