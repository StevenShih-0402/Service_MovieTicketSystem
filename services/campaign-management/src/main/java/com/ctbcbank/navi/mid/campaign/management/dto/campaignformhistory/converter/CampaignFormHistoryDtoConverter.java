package com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.converter;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.CampaignFormHistoryDto;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;

public class CampaignFormHistoryDtoConverter {
    public static CampaignFormHistoryDto parse(CampaignFormDto campaignFormDto, String updateEmployeeNo) {
        if (ObjectUtils.isEmpty(campaignFormDto)) {
            throw new NaviException(FabricResponseCode.INVALID_DATA, "campaignFormDto should not be null");
        }

        CampaignFormHistoryDto campaignFormHistoryDto = new CampaignFormHistoryDto();
        campaignFormHistoryDto.setCampaignFormNo(campaignFormDto.getCampaignFormNo());
        campaignFormHistoryDto.setCampaignNo(campaignFormDto.getCampaignNo());
        campaignFormHistoryDto.setName(campaignFormDto.getName());
        campaignFormHistoryDto.setDescription(campaignFormDto.getDescription());
        campaignFormHistoryDto.setCategory(campaignFormDto.getCategory());
        campaignFormHistoryDto.setIsImmediate(campaignFormDto.getIsImmediate());
        campaignFormHistoryDto.setImmediateTransactionCode(campaignFormDto.getImmediateTransactionCode());
        campaignFormHistoryDto.setStartDateTime(campaignFormDto.getStartDateTime());
        campaignFormHistoryDto.setEndDateTime(campaignFormDto.getEndDateTime());
        campaignFormHistoryDto.setReviewStatus(campaignFormDto.getReviewStatus());
        campaignFormHistoryDto.setCampaignFormType(campaignFormDto.getCampaignFormType());
        campaignFormHistoryDto.setIsListing(campaignFormDto.getIsListing());
        campaignFormHistoryDto.setGroupNodeData(campaignFormDto.getGroupNodeData());
        campaignFormHistoryDto.setCreateEmployeeNo(campaignFormDto.getCreateEmployeeNo());
        campaignFormHistoryDto.setUpdateEmployeeNo(updateEmployeeNo);
        campaignFormHistoryDto.setIsParticipantList(campaignFormDto.getIsParticipantList());
        return campaignFormHistoryDto;
    }
}
