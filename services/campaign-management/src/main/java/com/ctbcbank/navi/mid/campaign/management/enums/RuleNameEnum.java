package com.ctbcbank.navi.mid.campaign.management.enums;

import java.util.Optional;

public enum RuleNameEnum {
    TIMES_COUNT_RULE("TIMES_COUNT_RULE"),
    VALUE_COUNT_RULE("VALUE_COUNT_RULE"),
    SINGLE_COUNT_RULE("SINGLE_COUNT_RULE"),
    IS_NEW_DEP_BOOLEAN_RULE("IS_NEW_DEP_BOOLEAN_RULE"),
    IS_NEW_CREDIT_CARD_BOOLEAN_RULE("IS_NEW_DEP_BOOLEAN_RULE"),
    IS_VIP_BOOLEAN_RULE("IS_VIP_BOOLEAN_RULE"),
    ALWAYS_TRUE("ALWAYS_TRUE"),
    IS_TRUE("IS_TRUE");

    RuleNameEnum(String ruleName) {
    }

    public static Optional<RuleNameEnum> fromCode(String code) {
        for (RuleNameEnum ruleName : RuleNameEnum.values()) {
            if (ruleName.name().equals(code)) {
                return Optional.of(ruleName);
            }
        }
        return Optional.empty();
    }
}
