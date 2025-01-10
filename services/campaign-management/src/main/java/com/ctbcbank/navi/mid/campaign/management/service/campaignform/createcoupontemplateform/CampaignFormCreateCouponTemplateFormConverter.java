package com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcoupontemplateform;

import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.createcoupontemplateform.CampaignFormCreateCouponTemplateFormRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.createcoupontemplateform.CampaignFormCreateCouponTemplateFormRs;

public class CampaignFormCreateCouponTemplateFormConverter {

    public static CampaignFormCreateCouponTemplateFormRqBo parseRqToRqBo(CampaignFormCreateCouponTemplateFormRq campaignFormCreateCouponTemplateFormRq) {
        CampaignFormCreateCouponTemplateFormRqBo campaignFormCreateCouponTemplateFormRqBo = new CampaignFormCreateCouponTemplateFormRqBo();
        campaignFormCreateCouponTemplateFormRqBo.setCampaignFormNo(campaignFormCreateCouponTemplateFormRq.getCampaignFormNo());
        campaignFormCreateCouponTemplateFormRqBo.setCampaignNo(campaignFormCreateCouponTemplateFormRq.getCampaignNo());
        campaignFormCreateCouponTemplateFormRqBo.setCouponTemplateFormNo(campaignFormCreateCouponTemplateFormRq.getCouponTemplateFormNo());
        campaignFormCreateCouponTemplateFormRqBo.setCouponTemplateNo(campaignFormCreateCouponTemplateFormRq.getCouponTemplateNo());
        return campaignFormCreateCouponTemplateFormRqBo;
    }

    public static CampaignFormCreateCouponTemplateFormRs parseRsBoToRs(CampaignFormCreateCouponTemplateFormRsBo campaignFormCreateCouponTemplateFormRsBo) {
        CampaignFormCreateCouponTemplateFormRs campaignFormCreateCouponTemplateFormRs = new CampaignFormCreateCouponTemplateFormRs();
        campaignFormCreateCouponTemplateFormRs.setCampaignFormNo(campaignFormCreateCouponTemplateFormRsBo.getCampaignFormNo());
        campaignFormCreateCouponTemplateFormRs.setCampaignNo(campaignFormCreateCouponTemplateFormRsBo.getCampaignNo());
        campaignFormCreateCouponTemplateFormRs.setCouponTemplateFormNo(campaignFormCreateCouponTemplateFormRsBo.getCouponTemplateFormNo());
        campaignFormCreateCouponTemplateFormRs.setCouponTemplateNo(campaignFormCreateCouponTemplateFormRsBo.getCouponTemplateNo());
        return campaignFormCreateCouponTemplateFormRs;
    }

}
