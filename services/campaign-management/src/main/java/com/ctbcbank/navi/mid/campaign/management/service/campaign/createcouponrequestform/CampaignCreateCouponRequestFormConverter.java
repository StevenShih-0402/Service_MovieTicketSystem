package com.ctbcbank.navi.mid.campaign.management.service.campaign.createcouponrequestform;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignCreateCouponRequestFormRq;

public class CampaignCreateCouponRequestFormConverter {

    public static CampaignCreateCouponRequestFormRqBo parseRqToRqBo(CampaignCreateCouponRequestFormRq campaignCreateCouponRequestFormRq) {
        CampaignCreateCouponRequestFormRqBo campaignCreateCouponRequestFormRqBo = new CampaignCreateCouponRequestFormRqBo();
        campaignCreateCouponRequestFormRqBo.setCampaignNo(campaignCreateCouponRequestFormRq.getCampaignNo());
        campaignCreateCouponRequestFormRqBo.setCouponTemplateNo(campaignCreateCouponRequestFormRq.getCouponTemplateNo());
        campaignCreateCouponRequestFormRqBo.setCouponRequestFormNo(campaignCreateCouponRequestFormRq.getCouponRequestFormNo());
        return campaignCreateCouponRequestFormRqBo;
    }

}
