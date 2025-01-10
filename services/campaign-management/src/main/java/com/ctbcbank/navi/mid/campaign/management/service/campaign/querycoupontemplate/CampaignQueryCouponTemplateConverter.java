package com.ctbcbank.navi.mid.campaign.management.service.campaign.querycoupontemplate;

import java.util.ArrayList;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryCouponTemplateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryCouponTemplateRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.List;

public class CampaignQueryCouponTemplateConverter {

    public static CampaignQueryCouponTemplateRqBo parseRqToRqBo(CampaignQueryCouponTemplateRq campaignQueryCouponTemplateRq) {
        CampaignQueryCouponTemplateRqBo campaignQueryCouponTemplateRqBo = new CampaignQueryCouponTemplateRqBo();
        campaignQueryCouponTemplateRqBo.setCampaignNo(campaignQueryCouponTemplateRq.getCampaignNo());
        campaignQueryCouponTemplateRqBo.setCouponTemplateNo(campaignQueryCouponTemplateRq.getCouponTemplateNo());
        return campaignQueryCouponTemplateRqBo;
    }

    public static CampaignQueryCouponTemplateRs parseRsBoToRs(CampaignQueryCouponTemplateRsBo campaignQueryCouponTemplateRsBo) {
        CampaignQueryCouponTemplateRs campaignQueryCouponTemplateRs = new CampaignQueryCouponTemplateRs();
        campaignQueryCouponTemplateRs.setCampaignCouponTemplateList(getCampaignCouponTemplateList(campaignQueryCouponTemplateRsBo.getCampaignCouponTemplateList()));
        return campaignQueryCouponTemplateRs;
    }

    private static List<CampaignQueryCouponTemplateRs.CampaignCouponTemplate> getCampaignCouponTemplateList(List<CampaignQueryCouponTemplateRsBo.CampaignCouponTemplateBo> campaignCouponTemplateBoList) {
        List<CampaignQueryCouponTemplateRs.CampaignCouponTemplate> campaignCouponTemplatelist = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignCouponTemplateBoList)) {
            return campaignCouponTemplatelist;
        }
        campaignCouponTemplatelist = campaignCouponTemplateBoList.stream().map(x -> {
            CampaignQueryCouponTemplateRs.CampaignCouponTemplate campaignCouponTemplate = new CampaignQueryCouponTemplateRs.CampaignCouponTemplate();
            campaignCouponTemplate.setCampaignNo(x.getCampaignNo());
            campaignCouponTemplate.setCouponTemplateNo(x.getCouponTemplateNo());
            return campaignCouponTemplate;
        }).toList();
        return campaignCouponTemplatelist;

    }
}
