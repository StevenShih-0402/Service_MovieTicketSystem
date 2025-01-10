package com.ctbcbank.navi.mid.campaign.management.service.campaignform.updatereviewstatus;

import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.updatereviewstatus.CampaignFormUpdateReviewStatusRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.updatereviewstatus.CampaignFormUpdateReviewStatusRs;

public class CampaignFormUpdateReviewStatusConverter {

    public static CampaignFormUpdateReviewStatusRqBo parseRqToRqBo(CampaignFormUpdateReviewStatusRq campaignFormUpdateReviewStatusRq) {
        CampaignFormUpdateReviewStatusRqBo campaignFormUpdateReviewStatusRqBo = new CampaignFormUpdateReviewStatusRqBo();
        campaignFormUpdateReviewStatusRqBo.setCampaignFormNo(campaignFormUpdateReviewStatusRq.getCampaignFormNo());
        campaignFormUpdateReviewStatusRqBo.setReviewStatus(campaignFormUpdateReviewStatusRq.getReviewStatus());
        campaignFormUpdateReviewStatusRqBo.setUpdateEmployeeNo(campaignFormUpdateReviewStatusRq.getCampaignFormNo());
        return campaignFormUpdateReviewStatusRqBo;
    }

    public static CampaignFormUpdateReviewStatusRs parseRsBoToRs(CampaignFormUpdateReviewStatusRsBo campaignFormUpdateReviewStatusRsBo) {
        CampaignFormUpdateReviewStatusRs campaignFormUpdateReviewStatusRs = new CampaignFormUpdateReviewStatusRs();
        campaignFormUpdateReviewStatusRs.setCampaignFormNo(campaignFormUpdateReviewStatusRsBo.getCampaignFormNo());
        campaignFormUpdateReviewStatusRs.setReviewStatus(campaignFormUpdateReviewStatusRsBo.getReviewStatus());
        return campaignFormUpdateReviewStatusRs;
    }
}
