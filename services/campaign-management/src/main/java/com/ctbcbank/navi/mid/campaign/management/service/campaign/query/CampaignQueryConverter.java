package com.ctbcbank.navi.mid.campaign.management.service.campaign.query;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryRs;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CampaignQueryConverter {
    public static CampaignQueryRqBo parseRqToRqBo(CampaignQueryRq campaignQueryRq) {
        if (campaignQueryRq == null) {
            throw new NaviException(FabricResponseCode.INVALID_DATA);
        }
        CampaignQueryRqBo campaignQueryRqBo = new CampaignQueryRqBo();
        campaignQueryRqBo.setCategoryList(campaignQueryRq.getCategoryList());
        campaignQueryRqBo.setListingList(campaignQueryRq.getListingList());
        campaignQueryRqBo.setIsImmediate(campaignQueryRq.getIsImmediate());
        campaignQueryRqBo.setEndStartDateTime(campaignQueryRq.getEndStartDateTime());
        campaignQueryRqBo.setEndEndDatetime(campaignQueryRq.getEndEndDatetime());
        campaignQueryRqBo.setParticipantTypeList(campaignQueryRq.getParticipantTypeList());
        return campaignQueryRqBo;
    }

    public static CampaignQueryRs parseRsBoToRs(CampaignQueryRsBo campaignQueryRsBo) {
        if (campaignQueryRsBo == null) {
            return null;
        }
        CampaignQueryRs campaignQueryRs = new CampaignQueryRs();
        campaignQueryRs.setCampaignList(getCampaignList(campaignQueryRsBo.getCampaignList()));
        return campaignQueryRs;
    }

    private static List<CampaignQueryRs.Campaign> getCampaignList(List<CampaignQueryRsBo.CampaignBo> campaignBoList) {
        List<CampaignQueryRs.Campaign> campaignList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignBoList)) {
            return campaignList;
        }
        campaignBoList.forEach(x -> {
            CampaignQueryRs.Campaign campaign = new CampaignQueryRs.Campaign();
            campaign.setId(x.getId());
            campaign.setCampaignNo(x.getCampaignNo());
            campaign.setCategory(x.getCategory());
            campaign.setIsListing(x.getIsListing());
            campaign.setName(x.getName());
            campaign.setIsImmediate(x.getIsImmediate());
            campaign.setImmediateTransactionCode(x.getImmediateTransactionCode());
            campaign.setDescription(x.getDescription());
            campaign.setCreateEmployeeNo(x.getCreateEmployeeNo());
            campaign.setCreateDttm(x.getCreateDttm());
            campaign.setUpdateDttm(x.getUpdateDttm());
            campaign.setStartDateTime(x.getStartDateTime());
            campaign.setEndDateTime(x.getEndDateTime());
            campaignList.add(campaign);
        });
        return campaignList;
    }
}
