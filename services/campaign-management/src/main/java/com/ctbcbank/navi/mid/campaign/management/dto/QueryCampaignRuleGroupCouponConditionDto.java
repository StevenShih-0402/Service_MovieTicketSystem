package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCampaignRuleGroupCouponConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    BigInteger ruleGroupId;
}
