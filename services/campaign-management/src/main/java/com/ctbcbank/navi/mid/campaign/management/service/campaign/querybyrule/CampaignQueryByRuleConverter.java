package com.ctbcbank.navi.mid.campaign.management.service.campaign.querybyrule;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.querybyrule.CampaignQueryByRuleRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.querybyrule.CampaignQueryByRuleRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CampaignQueryByRuleConverter {

    public static CampaignQueryByRuleRqBo parseRqToRqBo(CampaignQueryByRuleRq campaignQueryByRuleRq) {
        CampaignQueryByRuleRqBo campaignQueryByRuleRqBo = new CampaignQueryByRuleRqBo();
        campaignQueryByRuleRqBo.setSize(campaignQueryByRuleRq.getSize());
        campaignQueryByRuleRqBo.setNumber(campaignQueryByRuleRq.getNumber());
        campaignQueryByRuleRqBo.setCampaignName(campaignQueryByRuleRq.getCampaignName());
        campaignQueryByRuleRqBo.setRuleList(getRuleBoList(campaignQueryByRuleRq.getRuleList()));
        return campaignQueryByRuleRqBo;

    }

    private static List<CampaignQueryByRuleRqBo.RuleBo> getRuleBoList(List<CampaignQueryByRuleRq.Rule> ruleList) {
        List<CampaignQueryByRuleRqBo.RuleBo> ruleBoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(ruleList)) {
            return ruleBoList;
        }
        ruleBoList = ruleList.stream().map(x -> {
            CampaignQueryByRuleRqBo.RuleBo ruleBo = new CampaignQueryByRuleRqBo.RuleBo();
            ruleBo.setTransactionCode(x.getTransactionCode());
            ruleBo.setRuleName(x.getRuleName());
            ruleBo.setRuleType(x.getRuleType());
            ruleBo.setRuleValue(x.getRuleValue());
            return ruleBo;
        }).toList();
        return ruleBoList;
    }

    public static CampaignQueryByRuleRs parseRsBoToRs(CampaignQueryByRuleRsBo campaignQueryByRuleRsBo) {
        CampaignQueryByRuleRs campaignQueryByRuleRs = new CampaignQueryByRuleRs();
        campaignQueryByRuleRs.setTotalElements(campaignQueryByRuleRsBo.getTotalElements());
        campaignQueryByRuleRs.setTotalPages(campaignQueryByRuleRsBo.getTotalPages());
        campaignQueryByRuleRs.setNumber(campaignQueryByRuleRsBo.getNumber());
        campaignQueryByRuleRs.setSize(campaignQueryByRuleRsBo.getSize());
        campaignQueryByRuleRs.setCampaignList(getCampaignList(campaignQueryByRuleRsBo.getCampaignList()));
        return campaignQueryByRuleRs;
    }

    private static List<CampaignQueryByRuleRs.Campaign> getCampaignList(List<CampaignQueryByRuleRsBo.CampaignBo> campaignBoList) {
        List<CampaignQueryByRuleRs.Campaign> campaignList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignBoList)) {
            return campaignList;
        }
        campaignList = campaignBoList.stream().map(x -> {
            CampaignQueryByRuleRs.Campaign campaign = new CampaignQueryByRuleRs.Campaign();
            campaign.setId(x.getId());
            campaign.setCampaignNo(x.getCampaignNo());
            campaign.setIsListing(x.getIsListing());
            campaign.setCategory(x.getCategory());
            campaign.setName(x.getName());
            campaign.setDescription(x.getDescription());
            campaign.setIsImmediate(x.getIsImmediate());
            campaign.setImmediateTransactionCode(x.getImmediateTransactionCode());
            campaign.setStartDateTime(x.getStartDateTime());
            campaign.setEndDateTime(x.getEndDateTime());
            campaign.setCreateEmployeeNo(x.getCreateEmployeeNo());
            campaign.setCreateDttm(x.getCreateDttm());
            campaign.setUpdateDttm(x.getUpdateDttm());
            return campaign;
        }).toList();
        return campaignList;
    }
}
