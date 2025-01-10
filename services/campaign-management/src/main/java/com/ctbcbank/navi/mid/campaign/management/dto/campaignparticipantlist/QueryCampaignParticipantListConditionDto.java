package com.ctbcbank.navi.mid.campaign.management.dto.campaignparticipantlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCampaignParticipantListConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String campaignNo;

    private List<String> campaignNoList;
    private List<String> participantListVersionList;
    private BigInteger ipNo;

}
