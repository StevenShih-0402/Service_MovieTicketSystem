package com.ctbcbank.navi.mid.campaign.management.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CampaignStatus implements BaseEnums {

    DRAFT("0", "DRAFT", 1, "草稿", "草稿"),
    PENDING_AUTHORIZATION_LISTING("1", "PENDING_AUTHORIZATION_LISTING", 2, "待上架授權", "待上架授權"),
    PENDING_AUTHORIZATION_UNLISTED("2", "PENDING_AUTHORIZATION_UNLISTED", 3, "待下架授權", "待下架授權"),
    LISTING("3", "LISTING", 4, "上架", "上架"),
    UNLISTED("4", "UNLISTED", 5, "下架", "下架"),
    LISTING_EDITING("5", "LISTING_EDITING", 6, "已上架編輯中", "已上架編輯中");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;

    public static CampaignStatus getByCode(String code) {
        return Arrays.stream(values())
                .filter(roleType -> roleType.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}
