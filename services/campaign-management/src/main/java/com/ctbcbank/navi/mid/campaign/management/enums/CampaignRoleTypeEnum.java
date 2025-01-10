package com.ctbcbank.navi.mid.campaign.management.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CampaignRoleTypeEnum implements BaseEnums {

    GENERAL("0", "GENERAL", 1, "一般", "一般"),
    ADMIN("1", "ADMIN", 2, "管理員", "管理員"),
    DEFAULT("1", "DEFAULT", 3, "預設", "預設");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;

}
