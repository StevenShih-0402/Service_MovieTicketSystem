package com.ctbcbank.navi.mid.campaign.management.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExchangeExtraRuleEnum implements BaseEnums {
    FROM_CURRENCY("0", "fromCurrency", 1, "原幣別", "原幣別"),
    TO_CURRENCY("1", "toCurrency", 2, "兌換幣別", "兌換幣別"),
    SOURCE_SYSTEM("2", "SOURCE_SYSTEM", 3, "系統來源", "系統來源"),
    BRANCH("3", "BRANCH", 4, "分行", "分行");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;

}
