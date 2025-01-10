package com.ctbcbank.navi.mid.campaign.management.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CampaignFormCommentTypeEnum implements BaseEnums {
    SUBMITTED("1", "SUBMITTED", 1, "送審", "送審"),
    APPROVED("2", "APPROVED", 2, "核准", "核准"),
    REJECTED("3", "REJECTED", 3, "拒絕", "拒絕"),
    CANCELLED("4", "CANCELLED", 4, "取消", "取消");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;
}
