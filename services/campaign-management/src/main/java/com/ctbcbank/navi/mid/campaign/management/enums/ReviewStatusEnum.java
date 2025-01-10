package com.ctbcbank.navi.mid.campaign.management.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ReviewStatusEnum implements BaseEnums {

    DRAFT("0", "DRAFT", 0, "草稿", "草稿"),
    SUBMITTED("1", "SUBMITTED", 1, "送審", "送審"),
    APPROVED("2", "APPROVED", 2, "核准", "核准"),
    REJECTED("3", "REJECTED", 3, "拒絕", "拒絕"),
    CANCELLED("4", "CANCELLED", 4, "取消", "取消"),
    UNDER_REVIEW("5", "UNDER_REVIEW", 5, "審核中", "審核中");


    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;

    public static ReviewStatusEnum getByCode(String code) {
        return Arrays.stream(values())
                .filter(roleType -> roleType.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}
