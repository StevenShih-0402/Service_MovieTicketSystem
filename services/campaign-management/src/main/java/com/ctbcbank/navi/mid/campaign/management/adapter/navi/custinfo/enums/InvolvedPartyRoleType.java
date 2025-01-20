package com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.enums;

import com.ibm.cbmp.fabric.foundation.enums.BaseEnums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum InvolvedPartyRoleType implements BaseEnums {

    CUSTOMER("0", "01", 1, "客戶", "Customer"),
    MEMBER("1", "02", 2, "會員", "Member"),
    VISITOR("2", "03", 3, "訪客", "Visitor");

    private final String index;
    private final String code;
    private final Integer intCode;
    private final String desc;
    private final String name;

    public static InvolvedPartyRoleType getByCode(String code) {
        return Arrays.stream(values())
                .filter(roleType -> roleType.getCode().equals(code))
                .findFirst()
                .orElse(VISITOR); // 如果沒有找到，預設給訪客
    }
}
