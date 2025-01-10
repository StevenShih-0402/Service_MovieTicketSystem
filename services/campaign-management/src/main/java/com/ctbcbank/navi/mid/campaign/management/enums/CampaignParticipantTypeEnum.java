package com.ctbcbank.navi.mid.campaign.management.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CampaignParticipantTypeEnum implements BaseEnums {
    FREE("0", "FREE", 0, "自由參與", "自由參與"),
    CUSTOMER_LIST("1", "CUSTOMER_LIST", 1, "客戶名單參與", "客戶名單參與"),
    ONLINE("2", "ONLINE", 2, "線上參與", "線上參與");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;
}
