package com.ctbcbank.navi.mid.campaign.management.dto.campaignformparticipantlist;

import com.ctbcbank.navi.mid.campaign.management.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignFormParticipantListDto extends BaseDto {
    private String campaignFormNo;
    private String campaignNo;
    private String idNo;
    private String cifNo;
    private BigInteger ipNo;
}
