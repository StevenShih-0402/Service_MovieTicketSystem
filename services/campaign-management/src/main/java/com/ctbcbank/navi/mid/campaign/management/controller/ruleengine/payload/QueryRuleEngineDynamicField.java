package com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRuleEngineDynamicField {
    private Map<String, String> dynamicFields;
}
