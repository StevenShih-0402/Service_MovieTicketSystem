package com.ctbcbank.navi.mid.campaign.management.service.campaign.create;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignCreateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignCreateRs;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class CreateCampaignConverter {
    public static CampaignCreateRs parseToRs(CampaignCreateRsBo campaignCreateRsBo) {
        CampaignCreateRs campaignCreateRs = new CampaignCreateRs();
        campaignCreateRs.setCampaignId(campaignCreateRsBo.getCampaignId());
        return campaignCreateRs;
    }

    public static CampaignCreateRqBo parseToBo(CampaignCreateRq campaignCreateRq) {
        CampaignCreateRqBo campaignCreateRqBo = new CampaignCreateRqBo();
        campaignCreateRqBo.setCampaignInfo(getCampaignInfoBo(campaignCreateRq.getCampaignInfo()));
        campaignCreateRqBo.setGroupNodes(getCampaignGroupNodeBoList(campaignCreateRq.getGroupNodes()));
        return campaignCreateRqBo;
    }

    private static CampaignCreateRqBo.CampaignInfoBo getCampaignInfoBo(CampaignCreateRq.CampaignInfo campaignInfo) {
        CampaignCreateRqBo.CampaignInfoBo campaignInfoBo = new CampaignCreateRqBo.CampaignInfoBo();
        if (ObjectUtils.isEmpty(campaignInfo)) {
            return campaignInfoBo;
        }
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

    private static List<CampaignCreateRqBo.CampaignGroupNodeBo> getCampaignGroupNodeBoList(List<CampaignCreateRq.CampaignGroupNode> groupNodes) {
        List<CampaignCreateRqBo.CampaignGroupNodeBo> groupNodesBo = new ArrayList<>();
        if (groupNodes == null) {
            return groupNodesBo;
        }
        for (CampaignCreateRq.CampaignGroupNode node : groupNodes) {
            groupNodesBo.add(copyToBo(node));
        }
        return groupNodesBo;
    }

    private static CampaignCreateRqBo.CampaignGroupNodeBo copyToBo(CampaignCreateRq.CampaignGroupNode node) {
        if (node == null) {
            return null;
        }

        CampaignCreateRqBo.CampaignGroupNodeBo nodeBo = new CampaignCreateRqBo().new CampaignGroupNodeBo();

        nodeBo.setGroupNodeData(copyToBoData(node.getGroupNodeData()));

        // Copy the children recursively
        if (node.getChildrenGroupNodes() != null) {
            List<CampaignCreateRqBo.CampaignGroupNodeBo> childrenBo = new ArrayList<>();
            for (CampaignCreateRq.CampaignGroupNode child : node.getChildrenGroupNodes()) {
                childrenBo.add(copyToBo(child));
            }
            nodeBo.setChildrenGroupNodes(childrenBo);
        }

        return nodeBo;
    }

    private static CampaignCreateRqBo.CampaignGroupNodeDataBo copyToBoData(CampaignCreateRq.CampaignGroupNodeData data) {
        if (data == null) {
            return null;
        }

        CampaignCreateRqBo.CampaignGroupNodeDataBo dataBo = new CampaignCreateRqBo().new CampaignGroupNodeDataBo();
        dataBo.setMatchCount(data.getMatchCount());
        dataBo.setRuleSettings(copyRuleSettingsToBo(data.getRuleSettings()));
        dataBo.setCouponTemplateSettings(copyCouponTemplateSettingsToBo(data.getCouponTemplateSettings()));

        return dataBo;
    }

    private static List<CampaignCreateRqBo.CampaignRuleSettingBo> copyRuleSettingsToBo(List<CampaignCreateRq.CampaignRuleSetting> ruleSettings) {
        if (ruleSettings == null) {
            return null;
        }

        List<CampaignCreateRqBo.CampaignRuleSettingBo> ruleSettingsBo = new ArrayList<>();
        for (CampaignCreateRq.CampaignRuleSetting setting : ruleSettings) {
            CampaignCreateRqBo.CampaignRuleSettingBo settingBo = new CampaignCreateRqBo().new CampaignRuleSettingBo();
            settingBo.setId(setting.getId());
            settingBo.setIsExtraRuleSetting(setting.getIsExtraRuleSetting());
            settingBo.setTransactionCode(setting.getTransactionCode());
            settingBo.setFieldName(setting.getFieldName());
            settingBo.setRuleName(setting.getRuleName());
            settingBo.setRuleValue(setting.getRuleValue());
            settingBo.setRuleType(setting.getRuleType());
            settingBo.setTransactionPayloadConfigId(setting.getTransactionPayloadConfigId());
            settingBo.setTransactionUrlConfigId(setting.getTransactionUrlConfigId());
            ruleSettingsBo.add(settingBo);
        }
        return ruleSettingsBo;
    }

    private static List<CampaignCreateRqBo.CampaignCouponTemplateSettingBo> copyCouponTemplateSettingsToBo(List<CampaignCreateRq.CampaignCouponTemplateSetting> couponSettings) {
        if (couponSettings == null) {
            return null;
        }

        List<CampaignCreateRqBo.CampaignCouponTemplateSettingBo> couponSettingsBo = new ArrayList<>();
        for (CampaignCreateRq.CampaignCouponTemplateSetting setting : couponSettings) {
            CampaignCreateRqBo.CampaignCouponTemplateSettingBo settingBo = new CampaignCreateRqBo().new CampaignCouponTemplateSettingBo();
            settingBo.setCouponTemplateNo(setting.getCouponTemplateNo());
            settingBo.setCouponTemplateFormNo(setting.getCouponTemplateFormNo());
            couponSettingsBo.add(settingBo);
        }
        return couponSettingsBo;
    }
}
