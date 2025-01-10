package com.ctbcbank.navi.mid.campaign.management.service.campaign.createcoupontemplate;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignCreateCouponTemplateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignCreateCouponTemplateRs;

public class CampaignCreateCouponTemplateConverter {

    public static CampaignCreateCouponTemplateRqBo parseRqToRqBo(CampaignCreateCouponTemplateRq campaignCreateCouponTemplateRq) {
        CampaignCreateCouponTemplateRqBo campaignCreateCouponTemplateRqBo = new CampaignCreateCouponTemplateRqBo();
        campaignCreateCouponTemplateRqBo.setCampaignNo(campaignCreateCouponTemplateRq.getCampaignNo());
        campaignCreateCouponTemplateRqBo.setCouponTemplateNo(campaignCreateCouponTemplateRq.getCouponTemplateNo());
        return campaignCreateCouponTemplateRqBo;
    }

    public static CampaignCreateCouponTemplateRs parseRsBoToRs(CampaignCreateCouponTemplateRsBo campaignCreateCouponTemplateRsBo) {
        CampaignCreateCouponTemplateRs campaignCreateCouponTemplateRs = new CampaignCreateCouponTemplateRs();
        campaignCreateCouponTemplateRs.setCampaignNo(campaignCreateCouponTemplateRsBo.getCampaignNo());
        campaignCreateCouponTemplateRs.setCouponTemplateNo(campaignCreateCouponTemplateRsBo.getCouponTemplateNo());
        return campaignCreateCouponTemplateRs;
    }

}
