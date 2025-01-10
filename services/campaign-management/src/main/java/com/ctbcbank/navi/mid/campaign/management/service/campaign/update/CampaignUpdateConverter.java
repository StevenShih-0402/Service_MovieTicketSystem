package com.ctbcbank.navi.mid.campaign.management.service.campaign.update;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignUpdateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignUpdateRs;

import java.util.ArrayList;
import java.util.List;

public class CampaignUpdateConverter {

    public static CampaignUpdateRqBo parseRqToRqBo(CampaignUpdateRq campaignUpdateRq) {
        CampaignUpdateRqBo campaignUpdateRqBo = new CampaignUpdateRqBo();
        campaignUpdateRqBo.setUpdateCampaignInfo(campaignUpdateRq.isUpdateCampaignInfo());
        campaignUpdateRqBo.setUpdateCampaignGroupNode(campaignUpdateRq.isUpdateCampaignGroupNode());
        campaignUpdateRqBo.setCampaignInfoBo(getCampaignInfoBo(campaignUpdateRq.getCampaignInfo()));
        campaignUpdateRqBo.setGroupNodes(getCampaignGroupNodeBoList(campaignUpdateRq.getGroupNodes()));
        return campaignUpdateRqBo;
    }

    public static CampaignUpdateRs parseRsBoToRs(CampaignUpdateRsBo campaignUpdateRsBo) {
        CampaignUpdateRs campaignUpdateRs = new CampaignUpdateRs();
        campaignUpdateRs.setCampaignId(campaignUpdateRsBo.getCampaignId());
        return campaignUpdateRs;
    }

    private static CampaignUpdateRqBo.CampaignInfoBo getCampaignInfoBo(CampaignUpdateRq.CampaignInfo campaignInfo) {
        CampaignUpdateRqBo.CampaignInfoBo campaignInfoBo = new CampaignUpdateRqBo.CampaignInfoBo();
        campaignInfoBo.setId(campaignInfo.getId());
        campaignInfoBo.setName(campaignInfo.getName());
        campaignInfoBo.setDescription(campaignInfo.getDescription());
        campaignInfoBo.setCategory(campaignInfo.getCategory());
        campaignInfoBo.setStartDateTime(campaignInfo.getStartDateTime());
        campaignInfoBo.setEndDateTime(campaignInfo.getEndDateTime());
        campaignInfoBo.setIsImmediate(campaignInfo.getIsImmediate());
        campaignInfoBo.setImmediateTransactionCode(campaignInfo.getImmediateTransactionCode());
        return campaignInfoBo;
    }

    private static List<CampaignUpdateRqBo.CampaignGroupNodeBo> getCampaignGroupNodeBoList(List<CampaignUpdateRq.CampaignGroupNode> groupNodes) {
        List<CampaignUpdateRqBo.CampaignGroupNodeBo> groupNodesBo = new ArrayList<>();
        if (groupNodes == null) {
            return groupNodesBo;
        }

        for (CampaignUpdateRq.CampaignGroupNode node : groupNodes) {
            groupNodesBo.add(copyToBo(node));
        }
        return groupNodesBo;
    }

    private static CampaignUpdateRqBo.CampaignGroupNodeBo copyToBo(CampaignUpdateRq.CampaignGroupNode node) {
        if (node == null) {
            return null;
        }

        CampaignUpdateRqBo.CampaignGroupNodeBo nodeBo = new CampaignUpdateRqBo.CampaignGroupNodeBo();
        nodeBo.setGroupNodeData(copyToBoData(node.getGroupNodeData()));

        // Copy the children recursively
        if (node.getChildrenGroupNodes() != null) {
            List<CampaignUpdateRqBo.CampaignGroupNodeBo> childrenBo = new ArrayList<>();
            for (CampaignUpdateRq.CampaignGroupNode child : node.getChildrenGroupNodes()) {
                childrenBo.add(copyToBo(child));
            }
            nodeBo.setChildrenGroupNodes(childrenBo);
        }
        return nodeBo;
    }

    private static CampaignUpdateRqBo.CampaignGroupNodeDataBo copyToBoData(CampaignUpdateRq.CampaignGroupNodeData data) {
        if (data == null) {
            return null;
        }
        CampaignUpdateRqBo.CampaignGroupNodeDataBo dataBo = new CampaignUpdateRqBo.CampaignGroupNodeDataBo();
        dataBo.setMatchCount(data.getMatchCount());
        dataBo.setRuleSettings(copyRuleSettingsToBo(data.getRuleSettings()));
        dataBo.setCouponTemplateSettings(copyCouponTemplateSettingsToBo(data.getCouponTemplateSettings()));
        return dataBo;
    }

    private static List<CampaignUpdateRqBo.CampaignRuleSettingBo> copyRuleSettingsToBo(List<CampaignUpdateRq.CampaignRuleSetting> ruleSettings) {
        if (ruleSettings == null) {
            return null;
        }
        List<CampaignUpdateRqBo.CampaignRuleSettingBo> ruleSettingsBo = new ArrayList<>();
        for (CampaignUpdateRq.CampaignRuleSetting setting : ruleSettings) {
            CampaignUpdateRqBo.CampaignRuleSettingBo settingBo = new CampaignUpdateRqBo.CampaignRuleSettingBo();
            settingBo.setIsExtraRuleSetting(setting.getIsExtraRuleSetting());
            settingBo.setTransactionCode(setting.getTransactionCode());
            settingBo.setFieldName(setting.getFieldName());
            settingBo.setRuleName(setting.getRuleName());
            settingBo.setRuleValue(setting.getRuleValue());
            settingBo.setRuleType(setting.getRuleType());
            ruleSettingsBo.add(settingBo);
        }
        return ruleSettingsBo;
    }

    private static List<CampaignUpdateRqBo.CampaignCouponTemplateSettingBo> copyCouponTemplateSettingsToBo(List<CampaignUpdateRq.CampaignCouponTemplateSetting> couponSettings) {
        if (couponSettings == null) {
            return null;
        }
        List<CampaignUpdateRqBo.CampaignCouponTemplateSettingBo> couponSettingsBo = new ArrayList<>();
        for (CampaignUpdateRq.CampaignCouponTemplateSetting setting : couponSettings) {
            CampaignUpdateRqBo.CampaignCouponTemplateSettingBo settingBo = new CampaignUpdateRqBo.CampaignCouponTemplateSettingBo();
            settingBo.setCouponTemplateNo(setting.getCouponTemplateNo());
            settingBo.setCouponTemplateFormNo(setting.getCouponTemplateFormNo());
            couponSettingsBo.add(settingBo);
        }
        return couponSettingsBo;
    }


}
