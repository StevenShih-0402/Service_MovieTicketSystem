package com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.addparticipantlist.CampaignAddParticipantListRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.addparticipantlist.CampaignAddParticipantListRs;

public class CampaignAddParticipantListConverter {

    public static CampaignAddParticipantListRqBo parseRqToRqBo(CampaignAddParticipantListRq campaignAddParticipantListRq) {
        CampaignAddParticipantListRqBo campaignAddParticipantListRqBo = new CampaignAddParticipantListRqBo();
        campaignAddParticipantListRqBo.setCampaignNo(campaignAddParticipantListRq.getCampaignNo());
        campaignAddParticipantListRqBo.setIpNo(campaignAddParticipantListRq.getIpNo());
        return campaignAddParticipantListRqBo;
    }

    public static CampaignAddParticipantListRs parseRsBoToRs(CampaignAddParticipantListRsBo campaignAddParticipantListRsBo) {
        CampaignAddParticipantListRs campaignAddParticipantListRs = new CampaignAddParticipantListRs();
        campaignAddParticipantListRs.setStatus(campaignAddParticipantListRsBo.getStatus());
        campaignAddParticipantListRs.setMessage(campaignAddParticipantListRsBo.getMessage());
        return campaignAddParticipantListRs;
    }

}
