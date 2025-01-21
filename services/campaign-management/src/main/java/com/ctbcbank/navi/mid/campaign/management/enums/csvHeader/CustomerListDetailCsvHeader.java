package com.ctbcbank.navi.mid.campaign.management.enums.csvHeader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerListDetailCsvHeader implements ValidateCsvHeader {
    ID_NO("0", "ID_NO", "ID No", "身分證字號", true, "idNo"),
    NAME("0", "NAME", "Name", "名字", true, "name");

    private final String index;
    private final String code;
    private final String desc;
    private final String name;
    private final boolean required;
    private final String attribute;
}
