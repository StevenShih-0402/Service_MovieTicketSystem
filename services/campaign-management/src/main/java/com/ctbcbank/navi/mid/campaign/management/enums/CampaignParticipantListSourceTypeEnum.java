package com.ctbcbank.navi.mid.campaign.management.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CampaignParticipantListSourceTypeEnum implements BaseEnums {
    CUSTOMER_LIST("0", "CUSTOMER_LIST", 1, "客戶名單", "客戶名單"),
    ONLINE_CLICK("1", "ONLINE_CLICK", 2, "線上點擊", "線上點擊");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;
}
