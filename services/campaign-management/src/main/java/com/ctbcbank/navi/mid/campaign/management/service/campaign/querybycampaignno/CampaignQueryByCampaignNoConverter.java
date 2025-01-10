package com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignno;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryByCampaignIdRs;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryByCampaignNoRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryByCampaignNoRs;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignid.CampaignQueryByCampaignIdBoRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CampaignQueryByCampaignNoConverter {

    public static CampaignQueryByCampaignNoRqBo parseRqToRqBo(CampaignQueryByCampaignNoRq campaignQueryByCampaignNoRq) {
        CampaignQueryByCampaignNoRqBo campaignQueryByCampaignNoRqBo = new CampaignQueryByCampaignNoRqBo();
        campaignQueryByCampaignNoRqBo.setCampaignNo(campaignQueryByCampaignNoRq.getCampaignNo());
        return campaignQueryByCampaignNoRqBo;
    }

    public static CampaignQueryByCampaignNoRs parseRsBoToRs(CampaignQueryByCampaignNoRsBo campaignQueryByCampaignNoRsBo) {
        CampaignQueryByCampaignNoRs campaignQueryByCampaignNoRs = new CampaignQueryByCampaignNoRs();
        campaignQueryByCampaignNoRs.setCampaignInfo(getCampaignInfo(campaignQueryByCampaignNoRsBo.getCampaignInfoBo()));
        campaignQueryByCampaignNoRs.setGroupNodes(getCampaignGroupNodeList(campaignQueryByCampaignNoRsBo.getGroupNodeBos()));
        return campaignQueryByCampaignNoRs;
    }

    private static CampaignQueryByCampaignNoRs.CampaignInfo getCampaignInfo(CampaignQueryByCampaignNoRsBo.CampaignInfoBo campaignInfoBo) {
        CampaignQueryByCampaignNoRs.CampaignInfo campaignInfo = new CampaignQueryByCampaignNoRs.CampaignInfo();
        campaignInfo.setId(campaignInfoBo.getId());
        campaignInfo.setCampaignNo(campaignInfoBo.getCampaignNo());
        campaignInfo.setIsListing(campaignInfoBo.getIsListing());
        campaignInfo.setCategory(campaignInfoBo.getCategory());
        campaignInfo.setName(campaignInfoBo.getName());
        campaignInfo.setDescription(campaignInfoBo.getDescription());
        campaignInfo.setIsImmediate(campaignInfoBo.getIsImmediate());
        campaignInfo.setImmediateTransactionCode(campaignInfoBo.getImmediateTransactionCode());
        campaignInfo.setStartDateTime(campaignInfoBo.getStartDateTime());
        campaignInfo.setEndDateTime(campaignInfoBo.getEndDateTime());
        campaignInfo.setCreateEmployeeNo(campaignInfoBo.getCreateEmployeeNo());
        campaignInfo.setIsParticipantList(campaignInfoBo.getIsParticipantList());
        campaignInfo.setCustomerListNo(campaignInfoBo.getCustomerListNo());
        campaignInfo.setParticipantType(campaignInfoBo.getParticipantType());
        campaignInfo.setParticipantListLimit(campaignInfoBo.getParticipantListLimit());
        campaignInfo.setCreateDttm(campaignInfoBo.getCreateDttm());
        campaignInfo.setUpdateDttm(campaignInfoBo.getUpdateDttm());
        return campaignInfo;
    }

    private static List<CampaignQueryByCampaignNoRs.CampaignGroupNode> getCampaignGroupNodeList(List<CampaignQueryByCampaignNoRsBo.CampaignGroupNodeBo> campaignGroupNodeBoList) {
        List<CampaignQueryByCampaignNoRs.CampaignGroupNode> groupNodes = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignGroupNodeBoList)) {
            return groupNodes;
        }
        for (CampaignQueryByCampaignNoRsBo.CampaignGroupNodeBo node : campaignGroupNodeBoList) {
            groupNodes.add(parseGroupNode(node));
        }
        return groupNodes;
    }

    private static CampaignQueryByCampaignNoRs.CampaignGroupNode parseGroupNode(CampaignQueryByCampaignNoRsBo.CampaignGroupNodeBo nodeBo) {
        if (nodeBo == null) {
            return null;
        }
        CampaignQueryByCampaignNoRs.CampaignGroupNode node = new CampaignQueryByCampaignNoRs.CampaignGroupNode();
        node.setGroupNodeData(parseGroupNodeData(nodeBo.getGroupNodeData()));
        if (!CollectionUtils.isEmpty(nodeBo.getChildrenGroupNodes())) {
            List<CampaignQueryByCampaignNoRs.CampaignGroupNode> childrenGroupNodes = new ArrayList<>();
            for (CampaignQueryByCampaignNoRsBo.CampaignGroupNodeBo child : nodeBo.getChildrenGroupNodes()) {
                childrenGroupNodes.add(parseGroupNode(child));
            }
            node.setChildrenGroupNodes(childrenGroupNodes);
        }
        return node;
    }

    private static CampaignQueryByCampaignNoRs.CampaignGroupNodeData parseGroupNodeData(CampaignQueryByCampaignNoRsBo.CampaignGroupNodeDataBo nodeDataBo) {
        if (nodeDataBo == null) {
            return null;
        }

        CampaignQueryByCampaignNoRs.CampaignGroupNodeData nodeData = new CampaignQueryByCampaignNoRs.CampaignGroupNodeData();
        nodeData.setMatchCount(nodeDataBo.getMatchCount());
        nodeData.setRuleSettings(parseRuleSetting(nodeDataBo.getRuleSettings()));
        nodeData.setCouponTemplateSettings(parseCouponTemplateSetting(nodeDataBo.getCouponTemplateSettings()));
        return nodeData;
    }

    private static List<CampaignQueryByCampaignNoRs.CampaignRuleSetting> parseRuleSetting(List<CampaignQueryByCampaignNoRsBo.CampaignRuleSettingBo> ruleSettingBos) {
        List<CampaignQueryByCampaignNoRs.CampaignRuleSetting> ruleSettings = new ArrayList<>();
        if (CollectionUtils.isEmpty(ruleSettingBos)) {
            return ruleSettings;
        }
        for (CampaignQueryByCampaignNoRsBo.CampaignRuleSettingBo settingBo : ruleSettingBos) {
            CampaignQueryByCampaignNoRs.CampaignRuleSetting setting = new CampaignQueryByCampaignNoRs.CampaignRuleSetting();
            setting.setTransactionCode(settingBo.getTransactionCode());
            setting.setFieldName(settingBo.getFieldName());
            setting.setRuleName(settingBo.getRuleName());
            setting.setRuleValue(settingBo.getRuleValue());
            setting.setRuleType(settingBo.getRuleType());
            ruleSettings.add(setting);
        }
        return ruleSettings;
    }

    private static List<CampaignQueryByCampaignNoRs.CampaignCouponTemplateSetting> parseCouponTemplateSetting(List<CampaignQueryByCampaignNoRsBo.CampaignCouponTemplateSettingBo> couponTemplateSettingBos) {
        List<CampaignQueryByCampaignNoRs.CampaignCouponTemplateSetting> couponTemplateSettings = new ArrayList<>();
        if (CollectionUtils.isEmpty(couponTemplateSettingBos)) {
            return couponTemplateSettings;
        }
        for (CampaignQueryByCampaignNoRsBo.CampaignCouponTemplateSettingBo settingBo : couponTemplateSettingBos) {
            CampaignQueryByCampaignNoRs.CampaignCouponTemplateSetting setting = new CampaignQueryByCampaignNoRs.CampaignCouponTemplateSetting();
            setting.setCouponTemplateNo(settingBo.getCouponTemplateNo());
            couponTemplateSettings.add(setting);
        }
        return couponTemplateSettings;
    }

}
