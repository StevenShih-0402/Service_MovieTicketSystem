package com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.impl;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormHistoryDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.CampaignFormHistoryDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.converter.CampaignFormHistoryDtoConverter;
import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.CampaignFormReviewRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.CampaignFormReviewService;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormReviewRejectedServiceImpl implements CampaignFormReviewService {
    private final String CLASS_NAME = CampaignFormReviewRejectedServiceImpl.class.getSimpleName();
    private final CampaignFormDao campaignFormDao;
    private final CampaignFormHistoryDao campaignFormHistoryDao;

    @Override
    @Transactional
    public void review(CampaignFormReviewRqBo campaignFormReviewRqBo) {
        String campaignFormNo = campaignFormReviewRqBo.getCampaignFormNo();
        ReviewStatusEnum reviewStatusEnum = campaignFormReviewRqBo.getReviewStatus();
        QueryCampaignFormConditionDto queryCampaignFormConditionDto = new QueryCampaignFormConditionDto();
        queryCampaignFormConditionDto.setCampaignFormNo(campaignFormNo);
        List<CampaignFormDto> campaignFormDtoList = campaignFormDao.queryCampaignForm(queryCampaignFormConditionDto);
        if (CollectionUtils.isEmpty(campaignFormDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "CampaignFormNo: " + campaignFormNo);
        }
        if (campaignFormDtoList.size() > 1) {
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "CampaignFormNo: " + campaignFormNo);
        }
        CampaignFormDto campaignFormDto = campaignFormDtoList.get(0);
        // 更新拒絕狀態
        campaignFormDto.setReviewStatus(reviewStatusEnum);
        CampaignFormDto saveCampaignFormDto = campaignFormDao.saveCampaignForm(campaignFormDto);
        log.info("[{}][review][saveCampaignForm: {}]", CLASS_NAME, saveCampaignFormDto);

        // 新增紀錄
        CampaignFormHistoryDto campaignFormHistoryDto = CampaignFormHistoryDtoConverter.parse(saveCampaignFormDto, campaignFormReviewRqBo.getUpdateEmployeeNo());
        campaignFormHistoryDao.saveCampaignFormHistory(campaignFormHistoryDto);
    }
}
