package com.ctbcbank.navi.mid.campaign.management.dto.campaignformparticipantlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCampaignFormParticipantListConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String campaignFormNo;
    
}
