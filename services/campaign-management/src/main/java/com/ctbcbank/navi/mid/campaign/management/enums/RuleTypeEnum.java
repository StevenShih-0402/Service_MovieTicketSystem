package com.ctbcbank.navi.mid.campaign.management.enums;

public enum RuleTypeEnum {
    EQUAL("EQUAL"),
    NOT_EQUAL("NOT_EQUAL"),
    GREATER_THAN("GREATER_THAN"),
    LESS_THAN("LESS_THAN"),
    GREATER_THAN_OR_EQUAL("GREATER_THAN_OR_EQUAL"),
    LIMIT("LIMIT"),
    IS("IS");;

    RuleTypeEnum(String ruleType) {
    }

    public static RuleTypeEnum fromCode(String code) {
        for (RuleTypeEnum ruleType : RuleTypeEnum.values()) {
            if (ruleType.name().equals(code)) {
                return ruleType;
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
