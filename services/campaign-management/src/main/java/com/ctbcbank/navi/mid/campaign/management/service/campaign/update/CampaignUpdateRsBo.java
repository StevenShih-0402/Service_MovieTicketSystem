package com.ctbcbank.navi.mid.campaign.management.service.campaign.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignUpdateRsBo {
    private BigInteger campaignId;
}
