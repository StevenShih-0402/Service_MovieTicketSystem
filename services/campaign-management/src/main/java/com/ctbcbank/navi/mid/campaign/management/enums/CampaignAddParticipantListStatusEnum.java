package com.ctbcbank.navi.mid.campaign.management.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CampaignAddParticipantListStatusEnum implements BaseEnums {
    COMPLETED("1", "COMPLETED", 1, "完成參與", "完成參與"),
    ALREADY("2", "ALREADY", 2, "已參與", "已參與"),
    NOT_ELIGIBLE("2", "NOT_ELIGIBLE", 2, "不可參與", "不可參與");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;
}
