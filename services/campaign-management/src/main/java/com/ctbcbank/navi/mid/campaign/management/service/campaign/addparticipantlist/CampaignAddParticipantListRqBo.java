package com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignAddParticipantListRqBo {
    private String campaignNo;
    private BigInteger ipNo;
}
