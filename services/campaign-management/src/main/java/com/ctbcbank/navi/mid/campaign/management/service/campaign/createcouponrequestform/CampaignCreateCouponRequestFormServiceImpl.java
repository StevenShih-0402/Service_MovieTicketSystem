package com.ctbcbank.navi.mid.campaign.management.service.campaign.createcouponrequestform;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCouponRequestFormDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignCouponRequestFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignCouponRequestFormConditionDto;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignCreateCouponRequestFormServiceImpl implements CampaignCreateCouponRequestFormService {
    private final String CLASS_NAME = CampaignCreateCouponRequestFormServiceImpl.class.getSimpleName();
    private final CampaignCouponRequestFormDao campaignCouponRequestFormDao;

    public void createCouponRequestForm(CampaignCreateCouponRequestFormRqBo campaignCreateCouponRequestFormRqBo) {
        QueryCampaignCouponRequestFormConditionDto queryCampaignCouponRequestFormConditionDto = new QueryCampaignCouponRequestFormConditionDto();
        queryCampaignCouponRequestFormConditionDto.setCampaignNo(campaignCreateCouponRequestFormRqBo.getCampaignNo());
        queryCampaignCouponRequestFormConditionDto.setCouponTemplateNo(campaignCreateCouponRequestFormRqBo.getCouponTemplateNo());
        queryCampaignCouponRequestFormConditionDto.setCouponRequestFormNo(campaignCreateCouponRequestFormRqBo.getCouponRequestFormNo());
        List<CampaignCouponRequestFormDto> campaignCouponRequestFormDtoList = campaignCouponRequestFormDao.queryCampaignCouponRequestForm(queryCampaignCouponRequestFormConditionDto);
        if (CollectionUtils.isEmpty(campaignCouponRequestFormDtoList)) {
            CampaignCouponRequestFormDto campaignCouponRequestFormDto = new CampaignCouponRequestFormDto();
            campaignCouponRequestFormDto.setCampaignNo(campaignCreateCouponRequestFormRqBo.getCampaignNo());
            campaignCouponRequestFormDto.setCouponTemplateNo(campaignCreateCouponRequestFormRqBo.getCouponTemplateNo());
            campaignCouponRequestFormDto.setCouponRequestFormNo(campaignCreateCouponRequestFormRqBo.getCouponRequestFormNo());
            CampaignCouponRequestFormDto saveCampaignCouponRequestFormDto = campaignCouponRequestFormDao.saveCampaignCouponRequestForm(campaignCouponRequestFormDto);
            log.info("[{}][createCouponRequestForm][insert saveCampaignCouponRequestFormDto: {}]", CLASS_NAME, saveCampaignCouponRequestFormDto);
        }
    }
}
