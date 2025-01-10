package com.ctbcbank.navi.mid.campaign.management.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CampaignUserStatusEnum implements BaseEnums {

    ACTIVATE("0", "ACTIVATE", 1, "啟用", "啟用"),
    DEACTIVATE("1", "DEACTIVATE", 2, "停用", "停用");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;

}
