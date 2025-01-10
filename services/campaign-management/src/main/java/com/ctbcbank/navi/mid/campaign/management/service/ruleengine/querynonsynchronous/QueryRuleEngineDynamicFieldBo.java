package com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querynonsynchronous;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRuleEngineDynamicFieldBo {
    private Map<String, String> dynamicFields;
}
