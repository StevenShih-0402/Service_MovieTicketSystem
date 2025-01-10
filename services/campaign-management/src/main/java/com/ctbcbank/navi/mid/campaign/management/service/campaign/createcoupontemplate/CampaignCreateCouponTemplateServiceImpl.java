package com.ctbcbank.navi.mid.campaign.management.service.campaign.createcoupontemplate;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCouponTemplateDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignCouponTemplateDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignCouponTemplateConditionDto;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignCreateCouponTemplateServiceImpl implements CampaignCreateCouponTemplateService {
    private final String CLASS_NAME = CampaignCreateCouponTemplateServiceImpl.class.getSimpleName();
    private final CampaignCouponTemplateDao campaignCouponTemplateDao;

    @Override
    public CampaignCreateCouponTemplateRsBo createCouponTemplate(CampaignCreateCouponTemplateRqBo campaignCreateCouponTemplateRqBo) {
        CampaignCreateCouponTemplateRsBo campaignCreateCouponTemplateRsBo = new CampaignCreateCouponTemplateRsBo();
        QueryCampaignCouponTemplateConditionDto queryCampaignCouponTemplateConditionDto = new QueryCampaignCouponTemplateConditionDto();
        queryCampaignCouponTemplateConditionDto.setCampaignNo(campaignCreateCouponTemplateRqBo.getCampaignNo());
        queryCampaignCouponTemplateConditionDto.setCouponTemplateNo(campaignCreateCouponTemplateRqBo.getCouponTemplateNo());
        List<CampaignCouponTemplateDto> campaignCouponTemplateDtoList = campaignCouponTemplateDao.queryCampaignCouponTemplate(queryCampaignCouponTemplateConditionDto);
        log.info("[{}][createCouponTemplate][campaignCouponTemplateDtoList: {}]", CLASS_NAME, campaignCouponTemplateDtoList);
        if (!CollectionUtils.isEmpty(campaignCouponTemplateDtoList)) {
            CampaignCouponTemplateDto campaignCouponTemplateDto = campaignCouponTemplateDtoList.get(0);
            campaignCreateCouponTemplateRsBo.setCampaignNo(campaignCouponTemplateDto.getCampaignNo());
            campaignCreateCouponTemplateRsBo.setCouponTemplateNo(campaignCouponTemplateDto.getCouponTemplateNo());
            return campaignCreateCouponTemplateRsBo;
        }
        CampaignCouponTemplateDto campaignCouponTemplateDto = new CampaignCouponTemplateDto();
        campaignCouponTemplateDto.setCampaignNo(campaignCreateCouponTemplateRqBo.getCampaignNo());
        campaignCouponTemplateDto.setCouponTemplateNo(campaignCreateCouponTemplateRqBo.getCouponTemplateNo());
        CampaignCouponTemplateDto saveCampaignCouponTemplateDto = campaignCouponTemplateDao.saveCampaignCouponTemplate(campaignCouponTemplateDto);
        campaignCreateCouponTemplateRsBo.setCampaignNo(saveCampaignCouponTemplateDto.getCampaignNo());
        campaignCreateCouponTemplateRsBo.setCouponTemplateNo(saveCampaignCouponTemplateDto.getCouponTemplateNo());
        return campaignCreateCouponTemplateRsBo;
    }

}
