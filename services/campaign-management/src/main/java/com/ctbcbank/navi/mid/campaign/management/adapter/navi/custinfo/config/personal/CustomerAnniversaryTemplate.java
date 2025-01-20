package com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.personal;


import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.swagger.ISO8601Template;

public abstract class CustomerAnniversaryTemplate {
    public static final String JSON_PROPERTY = "customerAnniversary";
    public static final String TITLE = "客戶週年日";
    public static final String DESCRIPTION = "可代表 個人生日/法人創立日期";
    public static final String EXAMPLE = ISO8601Template.DATE_EXAMPLE;
}
