package com.ctbcbank.navi.mid.campaign.management.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CampaignFormTypeEnum implements BaseEnums {
    CREATE("0", "CREATE", 1, "建立", "建立"),
    UPDATE("1", "UPDATE", 2, "更新", "更新");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;

    public static CampaignFormTypeEnum getByCode(String code) {
        return Arrays.stream(values())
                .filter(roleType -> roleType.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}
