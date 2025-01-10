package com.ctbcbank.navi.mid.campaign.management.service.campaignform.querybycampaignformno;

import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.querybycampaignformno.CampaignFormQueryByFormNoRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.querybycampaignformno.CampaignFormQueryByFormNoRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CampaignFormQueryByFormNoConverter {
    public static CampaignFormQueryByFormNoRqBo parseRqToRqBo(CampaignFormQueryByFormNoRq campaignFormQueryByFormNoRq) {
        CampaignFormQueryByFormNoRqBo campaignQueryByFormNoRqBo = new CampaignFormQueryByFormNoRqBo();
        campaignQueryByFormNoRqBo.setCampaignFormNo(campaignFormQueryByFormNoRq.getCampaignFormNo());
        return campaignQueryByFormNoRqBo;
    }

    public static CampaignFormQueryByFormNoRs parseRsBoToRs(CampaignFormQueryByFormNoRsBo campaignFormQueryByFormNoRsBo) {
        CampaignFormQueryByFormNoRs campaignQueryByFormNoRs = new CampaignFormQueryByFormNoRs();
        campaignQueryByFormNoRs.setCampaignFormInfo(getCampaignFormInfo(campaignFormQueryByFormNoRsBo.getCampaignFormInfo()));
        campaignQueryByFormNoRs.setCampaignFormCommentList(getCampaignFormCommentList(campaignFormQueryByFormNoRsBo.getCampaignFormCommentList()));
        campaignQueryByFormNoRs.setCampaignFormParticipantInfoList(getCampaignFormParticipantInfoList(campaignFormQueryByFormNoRsBo.getCampaignFormParticipantInfoList()));
        return campaignQueryByFormNoRs;
    }

    private static CampaignFormQueryByFormNoRs.CampaignFormInfo getCampaignFormInfo(CampaignFormQueryByFormNoRsBo.CampaignFormInfoBo campaignFormInfoBo) {
        CampaignFormQueryByFormNoRs.CampaignFormInfo campaignFormInfo = new CampaignFormQueryByFormNoRs.CampaignFormInfo();
        campaignFormInfo.setCampaignFormNo(campaignFormInfoBo.getCampaignFormNo());
        campaignFormInfo.setCampaignNo(campaignFormInfoBo.getCampaignNo());
        campaignFormInfo.setName(campaignFormInfoBo.getName());
        campaignFormInfo.setDescription(campaignFormInfoBo.getDescription());
        campaignFormInfo.setCategory(campaignFormInfoBo.getCategory());
        campaignFormInfo.setIsImmediate(campaignFormInfoBo.getIsImmediate());
        campaignFormInfo.setImmediateTransactionCode(campaignFormInfoBo.getImmediateTransactionCode());
        campaignFormInfo.setStartDateTime(campaignFormInfoBo.getStartDateTime());
        campaignFormInfo.setEndDateTime(campaignFormInfoBo.getEndDateTime());
        campaignFormInfo.setReviewStatus(campaignFormInfoBo.getReviewStatus());
        campaignFormInfo.setCampaignFormType(campaignFormInfoBo.getCampaignFormType());
        campaignFormInfo.setIsListing(campaignFormInfoBo.getIsListing());
        campaignFormInfo.setGroupNodeData(campaignFormInfoBo.getGroupNodeData());
        campaignFormInfo.setCreateEmployeeNo(campaignFormInfoBo.getCreateEmployeeNo());
        campaignFormInfo.setIsParticipantList(campaignFormInfoBo.getIsParticipantList());
        campaignFormInfo.setCustomerListNo(campaignFormInfoBo.getCustomerListNo());
        campaignFormInfo.setParticipantType(campaignFormInfoBo.getParticipantType());
        campaignFormInfo.setParticipantListLimit(campaignFormInfoBo.getParticipantListLimit());
        return campaignFormInfo;
    }

    private static List<CampaignFormQueryByFormNoRs.CampaignFormComment> getCampaignFormCommentList(List<CampaignFormQueryByFormNoRsBo.CampaignFormCommentBo> campaignFormCommentBoList) {
        List<CampaignFormQueryByFormNoRs.CampaignFormComment> campaignFormCommentList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignFormCommentBoList)) {
            return campaignFormCommentList;
        }
        campaignFormCommentList = campaignFormCommentBoList.stream().map(x -> {
            CampaignFormQueryByFormNoRs.CampaignFormComment campaignFormComment = new CampaignFormQueryByFormNoRs.CampaignFormComment();
            campaignFormComment.setCampaignFormCommentType(x.getCampaignFormCommentType());
            campaignFormComment.setComment(x.getComment());
            campaignFormComment.setCreateEmployeeNo(x.getCreateEmployeeNo());
            campaignFormComment.setCreateDttm(x.getCreateDttm());
            campaignFormComment.setUpdateDttm(x.getUpdateDttm());
            return campaignFormComment;
        }).toList();
        return campaignFormCommentList;
    }

    private static List<CampaignFormQueryByFormNoRs.CampaignFormParticipantInfo> getCampaignFormParticipantInfoList(List<CampaignFormQueryByFormNoRsBo.CampaignFormParticipantInfoBo> campaignFormParticipantInfoBoList) {
        List<CampaignFormQueryByFormNoRs.CampaignFormParticipantInfo> campaignFormParticipantInfoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignFormParticipantInfoBoList)) {
            return campaignFormParticipantInfoList;
        }
        campaignFormParticipantInfoList = campaignFormParticipantInfoBoList.stream().map(x -> {
            CampaignFormQueryByFormNoRs.CampaignFormParticipantInfo campaignFormParticipantInfo = new CampaignFormQueryByFormNoRs.CampaignFormParticipantInfo();
            campaignFormParticipantInfo.setCifNo(x.getCifNo());
            campaignFormParticipantInfo.setIdNo(x.getIdNo());
            campaignFormParticipantInfo.setIpNo(x.getIpNo());
            return campaignFormParticipantInfo;
        }).toList();
        return campaignFormParticipantInfoList;
    }


}
