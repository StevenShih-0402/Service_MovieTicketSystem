package com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum  InvolvedPartyStatus implements BaseEnums {

    ACTIVE("1", "01", 1, "生效", "Active"),
    INACTIVE("2", "02", 2, "失效", "Inactive"),
    UNKNOWN("3", "03", 3, "未知", "Unknown"),
    BANK_000("000", "000", 4, "生效", "Bank_000"),
    BANK_999("999", "999", 5, "失效", "Bank_999");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;

    public static InvolvedPartyStatus getByCode(String code) {
        return Arrays.stream(values())
                .filter(status -> status.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}
