package com.ctbcbank.navi.mid.campaign.management.dto.campaignparticipantlist;

import com.ctbcbank.navi.mid.campaign.management.dto.BaseDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantListSourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignParticipantListDto extends BaseDto {

    private String campaignNo;

    private BigInteger ipNo;

    private CampaignParticipantListSourceTypeEnum sourceType;

    private String customerListNo;

    private String participantListVersion;
}
