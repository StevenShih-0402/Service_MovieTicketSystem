package com.ctbcbank.navi.mid.campaign.management.dto;

import com.ctbcbank.navi.mid.campaign.management.enums.TransactionCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QueryCampaignRuleTransactionCodeConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<BigInteger> campaignIdList;

    private List<String> transactionCodeList;

    TransactionCodeEnum transactionCode;
}
