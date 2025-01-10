package com.ctbcbank.navi.mid.campaign.management.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CampaignCustomerListStatusEnum implements BaseEnums {

    NOT_QUERIED("0", "NOT_QUERIED", 0, "未查詢", "未查詢"),
    IN_PROGRESS("1", "IN_PROGRESS", 1, "查詢中", "查詢中"),
    COMPLETED("2", "COMPLETED", 2, "已查詢", "已查詢");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;

    public static CampaignCustomerListStatusEnum getByCode(String code) {
        return Arrays.stream(values())
                .filter(roleType -> roleType.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}
