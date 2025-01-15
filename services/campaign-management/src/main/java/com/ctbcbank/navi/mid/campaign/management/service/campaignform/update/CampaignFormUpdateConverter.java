package com.ctbcbank.navi.mid.campaign.management.service.campaignform.update;

import java.math.BigInteger;

import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.update.CampaignFormUpdateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.update.CampaignFormUpdateRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CampaignFormUpdateConverter {

    public static CampaignFormUpdateRqBo parseRqToRqBo(CampaignFormUpdateRq campaignFormUpdateRq) {
        CampaignFormUpdateRqBo campaignFormUpdateRqBo = new CampaignFormUpdateRqBo();
        campaignFormUpdateRqBo.setCampaignFormNo(campaignFormUpdateRq.getCampaignFormNo());
        campaignFormUpdateRqBo.setIsUpdateCampaignFormInfo(campaignFormUpdateRq.getIsUpdateCampaignFormInfo());
        campaignFormUpdateRqBo.setIsUpdateCampaignFormParticipantList(campaignFormUpdateRq.getIsUpdateCampaignFormParticipantList());
        campaignFormUpdateRqBo.setIsUpdateCampaignFormGroupNode(campaignFormUpdateRq.getIsUpdateCampaignFormGroupNode());
        campaignFormUpdateRqBo.setCampaignFormInfo(getCampaignFormInfoBo(campaignFormUpdateRq.getCampaignFormInfo()));
        campaignFormUpdateRqBo.setCustomerListNo(campaignFormUpdateRq.getCustomerListNo());
        campaignFormUpdateRqBo.setParticipantType(campaignFormUpdateRq.getParticipantType());
        campaignFormUpdateRqBo.setParticipantListLimit(campaignFormUpdateRq.getParticipantListLimit());
        campaignFormUpdateRqBo.setGroupNodeData(campaignFormUpdateRq.getGroupNodeData());
        campaignFormUpdateRqBo.setUpdateEmployeeNo(campaignFormUpdateRq.getUpdateEmployeeNo());
        return campaignFormUpdateRqBo;
    }

    private static CampaignFormUpdateRqBo.CampaignFormInfoBo getCampaignFormInfoBo(CampaignFormUpdateRq.CampaignFormInfo campaignFormInfo) {
        CampaignFormUpdateRqBo.CampaignFormInfoBo campaignFormInfoBo = new CampaignFormUpdateRqBo.CampaignFormInfoBo();
        campaignFormInfoBo.setName(campaignFormInfo.getName());
        campaignFormInfoBo.setDescription(campaignFormInfo.getDescription());
        campaignFormInfoBo.setCategory(campaignFormInfo.getCategory());
        campaignFormInfoBo.setIsImmediate(campaignFormInfo.getIsImmediate());
        campaignFormInfoBo.setImmediateTransactionCode(campaignFormInfo.getImmediateTransactionCode());
        campaignFormInfoBo.setStartDateTime(campaignFormInfo.getStartDateTime());
        campaignFormInfoBo.setEndDateTime(campaignFormInfo.getEndDateTime());
        campaignFormInfoBo.setIsListing(campaignFormInfo.getIsListing());
        return campaignFormInfoBo;
    }

    public static CampaignFormUpdateRs parseRsBoToRs(CampaignFormUpdateRsBo campaignFormUpdateRsBo) {
        CampaignFormUpdateRs campaignFormUpdateRs = new CampaignFormUpdateRs();
        campaignFormUpdateRs.setCampaignFormInfo(getCampaignFormInfo(campaignFormUpdateRsBo.getCampaignFormInfo()));
        campaignFormUpdateRs.setGroupNodeData(campaignFormUpdateRsBo.getGroupNodeData());
        return campaignFormUpdateRs;
    }

    private static CampaignFormUpdateRs.CampaignFormInfo getCampaignFormInfo(CampaignFormUpdateRsBo.CampaignFormInfoBo campaignFormInfoBo) {
        CampaignFormUpdateRs.CampaignFormInfo campaignFormInfo = new CampaignFormUpdateRs.CampaignFormInfo();
        campaignFormInfo.setCampaignFormNo(campaignFormInfoBo.getCampaignFormNo());
        campaignFormInfo.setCampaignNo(campaignFormInfoBo.getCampaignNo());
        campaignFormInfo.setName(campaignFormInfoBo.getName());
        campaignFormInfo.setDescription(campaignFormInfoBo.getDescription());
        campaignFormInfo.setCategory(campaignFormInfoBo.getCategory());
        campaignFormInfo.setIsImmediate(campaignFormInfoBo.getIsImmediate());
        campaignFormInfo.setImmediateTransactionCode(campaignFormInfoBo.getImmediateTransactionCode());
        campaignFormInfo.setStartDateTime(campaignFormInfoBo.getStartDateTime());
        campaignFormInfo.setEndDateTime(campaignFormInfoBo.getEndDateTime());
        campaignFormInfo.setCampaignFormType(campaignFormInfoBo.getCampaignFormType());
        campaignFormInfo.setIsListing(campaignFormInfoBo.getIsListing());
        return campaignFormInfo;
    }

}
