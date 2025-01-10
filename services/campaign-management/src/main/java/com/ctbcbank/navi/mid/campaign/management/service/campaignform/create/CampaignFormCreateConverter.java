package com.ctbcbank.navi.mid.campaign.management.service.campaignform.create;

import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.create.CampaignFormCreateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.create.CampaignFormCreateRs;

public class CampaignFormCreateConverter {
    public static CampaignFormCreateRqBo parseRqToRqBo(CampaignFormCreateRq campaignFormCreateRq) {
        CampaignFormCreateRqBo campaignFormCreateRqBo = new CampaignFormCreateRqBo();
        campaignFormCreateRqBo.setCampaignNo(campaignFormCreateRq.getCampaignNo());
        campaignFormCreateRqBo.setName(campaignFormCreateRq.getName());
        campaignFormCreateRqBo.setDescription(campaignFormCreateRq.getDescription());
        campaignFormCreateRqBo.setCategory(campaignFormCreateRq.getCategory());
        campaignFormCreateRqBo.setIsImmediate(campaignFormCreateRq.getIsImmediate());
        campaignFormCreateRqBo.setImmediateTransactionCode(campaignFormCreateRq.getImmediateTransactionCode());
        campaignFormCreateRqBo.setStartDateTime(campaignFormCreateRq.getStartDateTime());
        campaignFormCreateRqBo.setEndDateTime(campaignFormCreateRq.getEndDateTime());
        campaignFormCreateRqBo.setCampaignFormType(campaignFormCreateRq.getCampaignFormType());
        campaignFormCreateRqBo.setIsListing(campaignFormCreateRq.getIsListing());
        campaignFormCreateRqBo.setGroupNodeData(campaignFormCreateRq.getGroupNodeData());
        campaignFormCreateRqBo.setCreateEmployeeNo(campaignFormCreateRq.getCreateEmployeeNo());
        campaignFormCreateRqBo.setIsParticipantList(campaignFormCreateRq.getIsParticipantList());
        campaignFormCreateRqBo.setCustomerListNo(campaignFormCreateRq.getCustomerListNo());
        campaignFormCreateRqBo.setParticipantType(campaignFormCreateRq.getParticipantType());
        campaignFormCreateRqBo.setParticipantListLimit(campaignFormCreateRq.getParticipantListLimit());
        return campaignFormCreateRqBo;
    }

    public static CampaignFormCreateRs parseRsBoToRs(CampaignFormCreateRsBo campaignFormCreateRsBo) {
        CampaignFormCreateRs campaignFormCreateRs = new CampaignFormCreateRs();
        campaignFormCreateRs.setCampaignFormNo(campaignFormCreateRsBo.getCampaignFormNo());
        campaignFormCreateRs.setCampaignNo(campaignFormCreateRsBo.getCampaignNo());
        campaignFormCreateRs.setName(campaignFormCreateRsBo.getName());
        campaignFormCreateRs.setDescription(campaignFormCreateRsBo.getDescription());
        campaignFormCreateRs.setCategory(campaignFormCreateRsBo.getCategory());
        campaignFormCreateRs.setIsImmediate(campaignFormCreateRsBo.getIsImmediate());
        campaignFormCreateRs.setImmediateTransactionCode(campaignFormCreateRsBo.getImmediateTransactionCode());
        campaignFormCreateRs.setStartDateTime(campaignFormCreateRsBo.getStartDateTime());
        campaignFormCreateRs.setEndDateTime(campaignFormCreateRsBo.getEndDateTime());
        campaignFormCreateRs.setCampaignFormType(campaignFormCreateRsBo.getCampaignFormType());
        campaignFormCreateRs.setIsListing(campaignFormCreateRsBo.getIsListing());
        campaignFormCreateRs.setGroupNodeData(campaignFormCreateRsBo.getGroupNodeData());
        campaignFormCreateRs.setCreateEmployeeNo(campaignFormCreateRsBo.getCreateEmployeeNo());
        campaignFormCreateRs.setIsParticipantList(campaignFormCreateRsBo.getIsParticipantList());
        campaignFormCreateRs.setCustomerListNo(campaignFormCreateRsBo.getCustomerListNo());
        return campaignFormCreateRs;
    }


}
