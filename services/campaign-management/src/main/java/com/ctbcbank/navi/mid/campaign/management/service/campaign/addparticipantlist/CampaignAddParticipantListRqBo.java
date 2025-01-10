package com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantListSourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignAddParticipantListRqBo {
    private String campaignNo;
    private BigInteger ipNo;
}
