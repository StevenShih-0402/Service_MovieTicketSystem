package com.ctbcbank.navi.mid.campaign.management.service.campaignform.review;

import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.review.CampaignFormReviewRq;

public class CampaignFormReviewConverter {

    public static CampaignFormReviewRqBo parseRqToRqBo(CampaignFormReviewRq campaignFormReviewRq) {
        CampaignFormReviewRqBo campaignFormReviewRqBo = new CampaignFormReviewRqBo();
        campaignFormReviewRqBo.setCampaignFormNo(campaignFormReviewRq.getCampaignFormNo());
        campaignFormReviewRqBo.setReviewStatus(campaignFormReviewRq.getReviewStatus());
        campaignFormReviewRqBo.setUpdateEmployeeNo(campaignFormReviewRq.getUpdateEmployeeNo());
        return campaignFormReviewRqBo;
    }

}
