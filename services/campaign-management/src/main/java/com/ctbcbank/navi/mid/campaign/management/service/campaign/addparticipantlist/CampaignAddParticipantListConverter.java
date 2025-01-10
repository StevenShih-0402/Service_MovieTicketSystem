package com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.addparticipantlist.CampaignAddParticipantListRq;

public class CampaignAddParticipantListConverter {

    public static CampaignAddParticipantListRqBo parseRqToRqBo(CampaignAddParticipantListRq campaignAddParticipantListRq) {
        CampaignAddParticipantListRqBo campaignAddParticipantListRqBo = new CampaignAddParticipantListRqBo();
        campaignAddParticipantListRqBo.setCampaignNo(campaignAddParticipantListRq.getCampaignNo());
        campaignAddParticipantListRqBo.setIpNo(campaignAddParticipantListRq.getIpNo());
        return campaignAddParticipantListRqBo;
    }

}
