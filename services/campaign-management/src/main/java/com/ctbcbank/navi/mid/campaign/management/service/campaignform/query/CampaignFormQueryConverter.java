package com.ctbcbank.navi.mid.campaign.management.service.campaignform.query;

import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.query.CampaignFormQueryRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.query.CampaignFormQueryRs;

import java.util.List;

public class CampaignFormQueryConverter {
    public static CampaignFormQueryRqBo parseRqToRqBo(CampaignFormQueryRq campaignFormQueryRq) {
        CampaignFormQueryRqBo campaignFormQueryRqBo = new CampaignFormQueryRqBo();
        campaignFormQueryRqBo.setReviewStatusList(campaignFormQueryRq.getReviewStatusList());
        return campaignFormQueryRqBo;
    }

    public static CampaignFormQueryRs parseRsBoToRs(CampaignFormQueryRsBo campaignFormQueryRsBo) {
        CampaignFormQueryRs campaignFormQueryRs = new CampaignFormQueryRs();
        campaignFormQueryRs.setCampaignFormInfoList(getCampaignFormInfoList(campaignFormQueryRsBo.getCampaignFormInfoList()));
        return campaignFormQueryRs;
    }

    private static List<CampaignFormQueryRs.CampaignFormInfo> getCampaignFormInfoList(List<CampaignFormQueryRsBo.CampaignFormInfoBo> campaignFormInfoBoList) {
        List<CampaignFormQueryRs.CampaignFormInfo> campaignFormInfoList = campaignFormInfoBoList.stream().map(x -> {
            CampaignFormQueryRs.CampaignFormInfo campaignFormInfo = new CampaignFormQueryRs.CampaignFormInfo();
            campaignFormInfo.setCampaignFormNo(x.getCampaignFormNo());
            campaignFormInfo.setCampaignNo(x.getCampaignNo());
            campaignFormInfo.setName(x.getName());
            campaignFormInfo.setDescription(x.getDescription());
            campaignFormInfo.setCategory(x.getCategory());
            campaignFormInfo.setIsImmediate(x.getIsImmediate());
            campaignFormInfo.setImmediateTransactionCode(x.getImmediateTransactionCode());
            campaignFormInfo.setStartDateTime(x.getStartDateTime());
            campaignFormInfo.setEndDateTime(x.getEndDateTime());
            campaignFormInfo.setReviewStatus(x.getReviewStatus());
            campaignFormInfo.setCampaignFormType(x.getCampaignFormType());
            campaignFormInfo.setIsListing(x.getIsListing());
            campaignFormInfo.setGroupNodeData(x.getGroupNodeData());
            campaignFormInfo.setCreateEmployeeNo(x.getCreateEmployeeNo());
            campaignFormInfo.setCreateDttm(x.getCreateDttm());
            campaignFormInfo.setUpdateDttm(x.getUpdateDttm());
            return campaignFormInfo;
        }).toList();
        return campaignFormInfoList;
    }
}
