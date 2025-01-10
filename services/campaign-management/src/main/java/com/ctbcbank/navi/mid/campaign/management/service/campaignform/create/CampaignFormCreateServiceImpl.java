package com.ctbcbank.navi.mid.campaign.management.service.campaignform.create;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormHistoryDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.CampaignFormHistoryDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.converter.CampaignFormHistoryDtoConverter;
import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import com.ibm.cbmp.fabric.foundation.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormCreateServiceImpl implements CampaignFormCreateService {
    private final String CLASS_NAME = CampaignFormCreateServiceImpl.class.getSimpleName();
    private final CampaignFormDao campaignFormDao;
    private final CampaignFormHistoryDao campaignFormHistoryDao;

    @Override
    public CampaignFormCreateRsBo create(CampaignFormCreateRqBo campaignFormCreateRqBo) {
        CampaignFormDto campaignFormDto = getCampaignFormDto(campaignFormCreateRqBo);
        CampaignFormDto saveCampaignFormDto = campaignFormDao.saveCampaignForm(campaignFormDto);

        // 新增紀錄
        CampaignFormHistoryDto campaignFormHistoryDto = CampaignFormHistoryDtoConverter.parse(saveCampaignFormDto, campaignFormCreateRqBo.getCreateEmployeeNo());
        campaignFormHistoryDao.saveCampaignFormHistory(campaignFormHistoryDto);

        CampaignFormCreateRsBo campaignFormCreateRsBo = getCampaignFormCreateRsBo(saveCampaignFormDto);
        return campaignFormCreateRsBo;
    }

    private CampaignFormDto getCampaignFormDto(CampaignFormCreateRqBo campaignFormCreateRqBo) {
        CampaignFormDto campaignFormDto = new CampaignFormDto();
        campaignFormDto.setCampaignFormNo(UUIDUtils.getUUID());
        if (StringUtils.isBlank(campaignFormCreateRqBo.getCampaignNo())) {
            campaignFormDto.setCampaignNo(UUIDUtils.getUUID());
        } else {
            campaignFormDto.setCampaignNo(campaignFormCreateRqBo.getCampaignNo());
        }
        campaignFormDto.setName(campaignFormCreateRqBo.getName());
        campaignFormDto.setDescription(campaignFormCreateRqBo.getDescription());
        campaignFormDto.setCategory(campaignFormCreateRqBo.getCategory());
        campaignFormDto.setIsImmediate(campaignFormCreateRqBo.getIsImmediate());
        campaignFormDto.setImmediateTransactionCode(campaignFormCreateRqBo.getImmediateTransactionCode());
        campaignFormDto.setStartDateTime(campaignFormCreateRqBo.getStartDateTime());
        campaignFormDto.setEndDateTime(campaignFormCreateRqBo.getEndDateTime());
        campaignFormDto.setReviewStatus(ReviewStatusEnum.DRAFT);
        campaignFormDto.setCampaignFormType(campaignFormCreateRqBo.getCampaignFormType());
        campaignFormDto.setIsListing(campaignFormCreateRqBo.getIsListing());
        campaignFormDto.setGroupNodeData(campaignFormCreateRqBo.getGroupNodeData());
        campaignFormDto.setCreateEmployeeNo(campaignFormCreateRqBo.getCreateEmployeeNo());
        campaignFormDto.setIsParticipantList(campaignFormCreateRqBo.getIsParticipantList());
        campaignFormDto.setCustomerListNo(campaignFormCreateRqBo.getCustomerListNo());
        campaignFormDto.setParticipantType(campaignFormCreateRqBo.getParticipantType());
        campaignFormDto.setParticipantListLimit(campaignFormCreateRqBo.getParticipantListLimit());
        return campaignFormDto;
    }

    public CampaignFormCreateRsBo getCampaignFormCreateRsBo(CampaignFormDto campaignFormDto) {
        CampaignFormCreateRsBo campaignFormCreateRsBo = new CampaignFormCreateRsBo();
        campaignFormCreateRsBo.setCampaignFormNo(campaignFormDto.getCampaignFormNo());
        campaignFormCreateRsBo.setCampaignNo(campaignFormDto.getCampaignNo());
        campaignFormCreateRsBo.setName(campaignFormDto.getName());
        campaignFormCreateRsBo.setDescription(campaignFormDto.getDescription());
        campaignFormCreateRsBo.setCategory(campaignFormDto.getCategory());
        campaignFormCreateRsBo.setIsImmediate(campaignFormDto.getIsImmediate());
        campaignFormCreateRsBo.setImmediateTransactionCode(campaignFormDto.getImmediateTransactionCode());
        campaignFormCreateRsBo.setStartDateTime(campaignFormDto.getStartDateTime());
        campaignFormCreateRsBo.setEndDateTime(campaignFormDto.getEndDateTime());
        campaignFormCreateRsBo.setCampaignFormType(campaignFormDto.getCampaignFormType());
        campaignFormCreateRsBo.setIsListing(campaignFormDto.getIsListing());
        campaignFormCreateRsBo.setGroupNodeData(campaignFormDto.getGroupNodeData());
        campaignFormCreateRsBo.setCreateEmployeeNo(campaignFormDto.getCreateEmployeeNo());
        campaignFormCreateRsBo.setIsParticipantList(campaignFormDto.getIsParticipantList());
        campaignFormCreateRsBo.setCustomerListNo(campaignFormDto.getCustomerListNo());
        return campaignFormCreateRsBo;
    }
}
