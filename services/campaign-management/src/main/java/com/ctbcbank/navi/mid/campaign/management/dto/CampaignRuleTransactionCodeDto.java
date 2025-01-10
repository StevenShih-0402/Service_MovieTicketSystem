package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@SuperBuilder
public class CampaignRuleTransactionCodeDto {

    private BigInteger id;

    private BigInteger campaignId;

    private String transactionCode;

}
