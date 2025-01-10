package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryCampaignRuleSettingConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    BigInteger ruleGroupId;
}
