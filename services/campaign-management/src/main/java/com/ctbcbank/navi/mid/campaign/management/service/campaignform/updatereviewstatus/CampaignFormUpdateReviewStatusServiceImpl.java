package com.ctbcbank.navi.mid.campaign.management.service.campaignform.updatereviewstatus;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormHistoryDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.CampaignFormHistoryDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.converter.CampaignFormHistoryDtoConverter;
import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.update.CampaignFormUpdateServiceImpl;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormUpdateReviewStatusServiceImpl implements CampaignFormUpdateReviewStatusService {
    private final String CLASS_NAME = CampaignFormUpdateReviewStatusServiceImpl.class.getSimpleName();
    private final CampaignFormDao campaignFormDao;
    private final CampaignFormHistoryDao campaignFormHistoryDao;

    @Override
    public CampaignFormUpdateReviewStatusRsBo updateReviewStatus(CampaignFormUpdateReviewStatusRqBo campaignFormUpdateReviewStatusRqBo) {
        String campaignFormNo = campaignFormUpdateReviewStatusRqBo.getCampaignFormNo();
        QueryCampaignFormConditionDto campaignFormConditionDto = new QueryCampaignFormConditionDto();
        campaignFormConditionDto.setCampaignFormNo(campaignFormNo);
        List<CampaignFormDto> campaignFormDtoList = campaignFormDao.queryCampaignForm(campaignFormConditionDto);
        if (CollectionUtils.isEmpty(campaignFormDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "CampaignFormNo: " + campaignFormNo);
        }
        if (campaignFormDtoList.size() > 1) {
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "CampaignFormNo: " + campaignFormNo);
        }
        CampaignFormDto campaignFormDto = campaignFormDtoList.get(0);

        CampaignFormUpdateReviewStatusRsBo campaignFormUpdateReviewStatusRsBo = new CampaignFormUpdateReviewStatusRsBo();
        campaignFormUpdateReviewStatusRsBo.setCampaignFormNo(campaignFormDto.getCampaignFormNo());

        ReviewStatusEnum oldReviewStatus = campaignFormDto.getReviewStatus();
        ReviewStatusEnum newReviewStatus = campaignFormUpdateReviewStatusRqBo.getReviewStatus();
        // 相同狀態不用更新
        if (oldReviewStatus.compareTo(newReviewStatus) == 0) {
            campaignFormUpdateReviewStatusRsBo.setReviewStatus(campaignFormDto.getReviewStatus());
            return campaignFormUpdateReviewStatusRsBo;
        }
        // 更新狀態
        campaignFormDto.setReviewStatus(newReviewStatus);
        CampaignFormDto saveCampaignFormDto = campaignFormDao.saveCampaignForm(campaignFormDto);
        campaignFormUpdateReviewStatusRsBo.setReviewStatus(saveCampaignFormDto.getReviewStatus());

        // 新增紀錄
        CampaignFormHistoryDto campaignFormHistoryDto = CampaignFormHistoryDtoConverter.parse(saveCampaignFormDto, campaignFormUpdateReviewStatusRqBo.getUpdateEmployeeNo());
        campaignFormHistoryDao.saveCampaignFormHistory(campaignFormHistoryDto);

        return campaignFormUpdateReviewStatusRsBo;
    }

}
