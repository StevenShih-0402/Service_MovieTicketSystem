package com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcomment;

import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.createcomment.CampaignFormCreateCommentRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.createcomment.CampaignFormCreateCommentRs;

public class CampaignFormCreateCommentConverter {

    public static CampaignFormCreateCommentRqBo parseRqToRqBo(CampaignFormCreateCommentRq campaignFormCreateCommentRq) {
        CampaignFormCreateCommentRqBo campaignFormCreateCommentRqBo = new CampaignFormCreateCommentRqBo();
        campaignFormCreateCommentRqBo.setCampaignFormNo(campaignFormCreateCommentRq.getCampaignFormNo());
        campaignFormCreateCommentRqBo.setCampaignNo(campaignFormCreateCommentRq.getCampaignNo());
        campaignFormCreateCommentRqBo.setCampaignFormCommentType(campaignFormCreateCommentRq.getCampaignFormCommentType());
        campaignFormCreateCommentRqBo.setCampaignFormComment(campaignFormCreateCommentRq.getCampaignFormComment());
        campaignFormCreateCommentRqBo.setCreateEmployeeNo(campaignFormCreateCommentRq.getCreateEmployeeNo());
        return campaignFormCreateCommentRqBo;
    }

    public static CampaignFormCreateCommentRs parseRsBoToRs(CampaignFormCreateCommentRsBo campaignFormCreateCommentRsBo) {
        CampaignFormCreateCommentRs campaignFormCreateCommentRs = new CampaignFormCreateCommentRs();
        campaignFormCreateCommentRs.setCampaignFormNo(campaignFormCreateCommentRsBo.getCampaignFormNo());
        campaignFormCreateCommentRs.setCampaignNo(campaignFormCreateCommentRsBo.getCampaignNo());
        campaignFormCreateCommentRs.setCampaignFormCommentType(campaignFormCreateCommentRsBo.getCampaignFormCommentType());
        campaignFormCreateCommentRs.setCampaignFormComment(campaignFormCreateCommentRsBo.getCampaignFormComment());
        return campaignFormCreateCommentRs;
    }
}
