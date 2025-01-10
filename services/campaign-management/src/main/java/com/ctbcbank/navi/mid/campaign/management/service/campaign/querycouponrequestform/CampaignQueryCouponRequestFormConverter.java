package com.ctbcbank.navi.mid.campaign.management.service.campaign.querycouponrequestform;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryCouponRequestFormRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryCouponRequestFormRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CampaignQueryCouponRequestFormConverter {

    public static CampaignQueryCouponRequestFormRqBo parseRqToRqBo(CampaignQueryCouponRequestFormRq campaignQueryCouponRequestFormRq) {
        CampaignQueryCouponRequestFormRqBo campaignQueryCouponRequestFormRqBo = new CampaignQueryCouponRequestFormRqBo();
        campaignQueryCouponRequestFormRqBo.setCampaignNo(campaignQueryCouponRequestFormRq.getCampaignNo());
        campaignQueryCouponRequestFormRqBo.setCouponTemplateNo(campaignQueryCouponRequestFormRq.getCouponTemplateNo());
        campaignQueryCouponRequestFormRqBo.setCouponRequestFormNo(campaignQueryCouponRequestFormRq.getCouponRequestFormNo());
        return campaignQueryCouponRequestFormRqBo;
    }

    public static CampaignQueryCouponRequestFormRs parseRsBoToRs(CampaignQueryCouponRequestFormRsBo campaignQueryCouponRequestFormRsBo) {
        CampaignQueryCouponRequestFormRs campaignQueryCouponRequestFormRs = new CampaignQueryCouponRequestFormRs();
        campaignQueryCouponRequestFormRs.setCampaignCouponRequestFormList(getCampaignCouponRequestFormList(campaignQueryCouponRequestFormRsBo.getCampaignCouponRequestFormBoList()));
        return campaignQueryCouponRequestFormRs;
    }

    private static List<CampaignQueryCouponRequestFormRs.CampaignCouponRequestForm> getCampaignCouponRequestFormList(List<CampaignQueryCouponRequestFormRsBo.CampaignCouponRequestFormBo> campaignCouponRequestFormBoList) {
        List<CampaignQueryCouponRequestFormRs.CampaignCouponRequestForm> campaignCouponRequestFormList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignCouponRequestFormBoList)) {
            return campaignCouponRequestFormList;
        }
        campaignCouponRequestFormList = campaignCouponRequestFormBoList.stream().map(x -> {
            CampaignQueryCouponRequestFormRs.CampaignCouponRequestForm campaignCouponRequestForm = new CampaignQueryCouponRequestFormRs.CampaignCouponRequestForm();
            campaignCouponRequestForm.setCampaignNo(x.getCampaignNo());
            campaignCouponRequestForm.setCouponTemplateNo(x.getCouponTemplateNo());
            campaignCouponRequestForm.setCouponRequestFormNo(x.getCouponRequestFormNo());
            return campaignCouponRequestForm;
        }).toList();
        return campaignCouponRequestFormList;
    }
}
