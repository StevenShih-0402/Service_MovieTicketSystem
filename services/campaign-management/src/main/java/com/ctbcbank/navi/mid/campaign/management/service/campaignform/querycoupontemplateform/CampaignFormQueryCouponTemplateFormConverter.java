package com.ctbcbank.navi.mid.campaign.management.service.campaignform.querycoupontemplateform;

import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.querycoupontemplateform.CampaignFormQueryCouponTemplateFormRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.querycoupontemplateform.CampaignFormQueryCouponTemplateFormRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CampaignFormQueryCouponTemplateFormConverter {

    public static CampaignFormQueryCouponTemplateFormRqBo parseRqToRqBo(CampaignFormQueryCouponTemplateFormRq campaignFormQueryCouponTemplateFormRq) {
        CampaignFormQueryCouponTemplateFormRqBo campaignFormQueryCouponTemplateFormRqBo = new CampaignFormQueryCouponTemplateFormRqBo();
        campaignFormQueryCouponTemplateFormRqBo.setCampaignFormNo(campaignFormQueryCouponTemplateFormRq.getCampaignFormNo());
        campaignFormQueryCouponTemplateFormRqBo.setCouponTemplateFormNo(campaignFormQueryCouponTemplateFormRq.getCouponTemplateFormNo());
        return campaignFormQueryCouponTemplateFormRqBo;
    }

    public static CampaignFormQueryCouponTemplateFormRs parseRsBoToRs(CampaignFormQueryCouponTemplateFormRsBo campaignFormQueryCouponTemplateFormRsBo) {
        CampaignFormQueryCouponTemplateFormRs campaignFormQueryCouponTemplateFormRs = new CampaignFormQueryCouponTemplateFormRs();
        campaignFormQueryCouponTemplateFormRs.setCampaignFormCouponTemplateFormList(
                getCampaignFormCouponTemplateFormList(campaignFormQueryCouponTemplateFormRsBo.getCampaignFormCouponTemplateFormList()));
        return campaignFormQueryCouponTemplateFormRs;
    }

    private static List<CampaignFormQueryCouponTemplateFormRs.CampaignFormCouponTemplateForm> getCampaignFormCouponTemplateFormList(List<CampaignFormQueryCouponTemplateFormRsBo.CampaignFormCouponTemplateFormBo> campaignFormCouponTemplateFormBoList) {
        List<CampaignFormQueryCouponTemplateFormRs.CampaignFormCouponTemplateForm> campaignFormCouponTemplateFormList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignFormCouponTemplateFormBoList)) {
            return campaignFormCouponTemplateFormList;
        }
        campaignFormCouponTemplateFormList = campaignFormCouponTemplateFormBoList.stream().map(x -> {
            CampaignFormQueryCouponTemplateFormRs.CampaignFormCouponTemplateForm campaignFormCouponTemplateForm = new CampaignFormQueryCouponTemplateFormRs.CampaignFormCouponTemplateForm();
            campaignFormCouponTemplateForm.setCampaignFormNo(x.getCampaignFormNo());
            campaignFormCouponTemplateForm.setCouponTemplateFormNo(x.getCouponTemplateFormNo());
            return campaignFormCouponTemplateForm;
        }).toList();
        return campaignFormCouponTemplateFormList;
    }
}
