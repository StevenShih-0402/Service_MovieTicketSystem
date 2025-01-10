package com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignid;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryByCampaignIdRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryByCampaignIdRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CampaignQueryByCampaignIdConverter {

    public static CampaignQueryByCampaignIdBoRq parseRqToBoRq(CampaignQueryByCampaignIdRq campaignQueryByCampaignIdRq) {
        CampaignQueryByCampaignIdBoRq campaignQueryByCampaignIdBoRq = new CampaignQueryByCampaignIdBoRq();
        campaignQueryByCampaignIdBoRq.setCampaignId(campaignQueryByCampaignIdRq.getCampaignId());
        return campaignQueryByCampaignIdBoRq;
    }

    public static CampaignQueryByCampaignIdRs parseBoRsToRs(CampaignQueryByCampaignIdBoRs campaignQueryByCampaignIdBoRs) {
        CampaignQueryByCampaignIdRs campaignQueryByCampaignIdRs = new CampaignQueryByCampaignIdRs();
        campaignQueryByCampaignIdRs.setCampaign(getCampaignInfo(campaignQueryByCampaignIdBoRs.getCampaign()));
        campaignQueryByCampaignIdRs.setGroupNodes(getCampaignGroupNodeList(campaignQueryByCampaignIdBoRs.getGroupNodes()));
        return campaignQueryByCampaignIdRs;
    }

    public static CampaignQueryByCampaignIdRs.CampaignInfo getCampaignInfo(CampaignQueryByCampaignIdBoRs.CampaignInfoBo campaignInfoBo) {
        CampaignQueryByCampaignIdRs.CampaignInfo campaignInfo = new CampaignQueryByCampaignIdRs.CampaignInfo();
        campaignInfo.setId(campaignInfoBo.getId());
        campaignInfo.setStatus(campaignInfoBo.getStatus());
        campaignInfo.setCategory(campaignInfoBo.getCategory());
        campaignInfo.setName(campaignInfoBo.getName());
        campaignInfo.setDescription(campaignInfoBo.getDescription());
        campaignInfo.setIsImmediate(campaignInfoBo.getIsImmediate());
        campaignInfo.setImmediateTransactionCode(campaignInfoBo.getImmediateTransactionCode());
        campaignInfo.setStartDateTime(campaignInfoBo.getStartDateTime());
        campaignInfo.setEndDateTime(campaignInfoBo.getEndDateTime());
        campaignInfo.setCreateDttm(campaignInfoBo.getCreateDttm());
        campaignInfo.setUpdateDttm(campaignInfoBo.getUpdateDttm());
        return campaignInfo;
    }

    private static List<CampaignQueryByCampaignIdRs.CampaignGroupNode> getCampaignGroupNodeList(List<CampaignQueryByCampaignIdBoRs.CampaignGroupNodeBo> campaignGroupNodeBoList) {
        List<CampaignQueryByCampaignIdRs.CampaignGroupNode> groupNodes = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignGroupNodeBoList)) {
            return groupNodes;
        }
        for (CampaignQueryByCampaignIdBoRs.CampaignGroupNodeBo node : campaignGroupNodeBoList) {
            groupNodes.add(parseGroupNode(node));
        }
        return groupNodes;
    }

    private static CampaignQueryByCampaignIdRs.CampaignGroupNode parseGroupNode(CampaignQueryByCampaignIdBoRs.CampaignGroupNodeBo nodeBo) {
        if (nodeBo == null) {
            return null;
        }
        CampaignQueryByCampaignIdRs.CampaignGroupNode node = new CampaignQueryByCampaignIdRs.CampaignGroupNode();
        node.setGroupNodeData(parseGroupNodeData(nodeBo.getGroupNodeData()));
        if (!CollectionUtils.isEmpty(nodeBo.getChildrenGroupNodes())) {
            List<CampaignQueryByCampaignIdRs.CampaignGroupNode> childrenGroupNodes = new ArrayList<>();
            for (CampaignQueryByCampaignIdBoRs.CampaignGroupNodeBo child : nodeBo.getChildrenGroupNodes()) {
                childrenGroupNodes.add(parseGroupNode(child));
            }
            node.setChildrenGroupNodes(childrenGroupNodes);
        }
        return node;
    }

    private static CampaignQueryByCampaignIdRs.CampaignGroupNodeData parseGroupNodeData(CampaignQueryByCampaignIdBoRs.CampaignGroupNodeDataBo nodeDataBo) {
        if (nodeDataBo == null) {
            return null;
        }

        CampaignQueryByCampaignIdRs.CampaignGroupNodeData nodeData = new CampaignQueryByCampaignIdRs.CampaignGroupNodeData();
        nodeData.setMatchCount(nodeDataBo.getMatchCount());
        nodeData.setRuleSettings(parseRuleSetting(nodeDataBo.getRuleSettings()));
        nodeData.setCouponTemplateSettings(parseCouponTemplateSetting(nodeDataBo.getCouponTemplateSettings()));
        return nodeData;
    }

    private static List<CampaignQueryByCampaignIdRs.CampaignRuleSetting> parseRuleSetting(List<CampaignQueryByCampaignIdBoRs.CampaignRuleSettingBo> ruleSettingBos) {
        List<CampaignQueryByCampaignIdRs.CampaignRuleSetting> ruleSettings = new ArrayList<>();
        if (CollectionUtils.isEmpty(ruleSettingBos)) {
            return ruleSettings;
        }
        for (CampaignQueryByCampaignIdBoRs.CampaignRuleSettingBo settingBo : ruleSettingBos) {
            CampaignQueryByCampaignIdRs.CampaignRuleSetting setting = new CampaignQueryByCampaignIdRs.CampaignRuleSetting();
            setting.setId(settingBo.getId());
            setting.setRuleSetting(settingBo.isRuleSetting());
            setting.setTransactionCode(settingBo.getTransactionCode());
            setting.setFieldName(settingBo.getFieldName());
            setting.setRuleName(settingBo.getRuleName());
            setting.setRuleValue(settingBo.getRuleValue());
            setting.setRuleType(settingBo.getRuleType());
            ruleSettings.add(setting);
        }
        return ruleSettings;
    }

    private static List<CampaignQueryByCampaignIdRs.CampaignCouponTemplateSetting> parseCouponTemplateSetting(List<CampaignQueryByCampaignIdBoRs.CampaignCouponTemplateSettingBo> couponTemplateSettingBos) {
        List<CampaignQueryByCampaignIdRs.CampaignCouponTemplateSetting> couponTemplateSettings = new ArrayList<>();
        if (CollectionUtils.isEmpty(couponTemplateSettingBos)) {
            return couponTemplateSettings;
        }
        for (CampaignQueryByCampaignIdBoRs.CampaignCouponTemplateSettingBo settingBo : couponTemplateSettingBos) {
            CampaignQueryByCampaignIdRs.CampaignCouponTemplateSetting setting = new CampaignQueryByCampaignIdRs.CampaignCouponTemplateSetting();
            setting.setCouponTemplateNo(settingBo.getCouponTemplateNo());
            setting.setCouponTemplateFormNo(settingBo.getCouponTemplateFormNo());
            couponTemplateSettings.add(setting);
        }
        return couponTemplateSettings;
    }


}
