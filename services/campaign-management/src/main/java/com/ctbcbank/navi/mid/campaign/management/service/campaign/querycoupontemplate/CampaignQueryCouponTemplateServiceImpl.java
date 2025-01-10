package com.ctbcbank.navi.mid.campaign.management.service.campaign.querycoupontemplate;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCouponTemplateDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignCouponTemplateDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignCouponTemplateConditionDto;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.createcoupontemplate.CampaignCreateCouponTemplateServiceImpl;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignQueryCouponTemplateServiceImpl implements CampaignQueryCouponTemplateService {
    private final String CLASS_NAME = CampaignQueryCouponTemplateServiceImpl.class.getSimpleName();
    private final CampaignCouponTemplateDao campaignCouponTemplateDao;

    @Override
    public CampaignQueryCouponTemplateRsBo queryCouponTemplate(CampaignQueryCouponTemplateRqBo campaignQueryCouponTemplateRqBo) {
        QueryCampaignCouponTemplateConditionDto queryCampaignCouponTemplateConditionDto = getQueryCampaignCouponTemplateConditionDto(campaignQueryCouponTemplateRqBo);
        List<CampaignCouponTemplateDto> campaignCouponTemplateDtoList = campaignCouponTemplateDao.queryCampaignCouponTemplate(queryCampaignCouponTemplateConditionDto);
        log.info("[{}][queryCouponTemplate][campaignCouponTemplateDtoList: {}]", CLASS_NAME, campaignCouponTemplateDtoList);
        CampaignQueryCouponTemplateRsBo campaignQueryCouponTemplateRsBo = new CampaignQueryCouponTemplateRsBo();
        campaignQueryCouponTemplateRsBo.setCampaignCouponTemplateList(getCampaignCouponTemplateBoList(campaignCouponTemplateDtoList));
        return campaignQueryCouponTemplateRsBo;
    }

    private QueryCampaignCouponTemplateConditionDto getQueryCampaignCouponTemplateConditionDto(CampaignQueryCouponTemplateRqBo campaignQueryCouponTemplateRqBo) {
        QueryCampaignCouponTemplateConditionDto queryCampaignCouponTemplateConditionDto = new QueryCampaignCouponTemplateConditionDto();
        queryCampaignCouponTemplateConditionDto.setCampaignNo(campaignQueryCouponTemplateRqBo.getCampaignNo());
        queryCampaignCouponTemplateConditionDto.setCouponTemplateNo(campaignQueryCouponTemplateRqBo.getCouponTemplateNo());
        return queryCampaignCouponTemplateConditionDto;
    }

    private List<CampaignQueryCouponTemplateRsBo.CampaignCouponTemplateBo> getCampaignCouponTemplateBoList(List<CampaignCouponTemplateDto> campaignCouponTemplateDtoList) {
        List<CampaignQueryCouponTemplateRsBo.CampaignCouponTemplateBo> campaignCouponTemplateBoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignCouponTemplateDtoList)) {
            return campaignCouponTemplateBoList;
        }
        campaignCouponTemplateBoList = campaignCouponTemplateDtoList.stream().map(x -> {
            CampaignQueryCouponTemplateRsBo.CampaignCouponTemplateBo campaignCouponTemplateBo = new CampaignQueryCouponTemplateRsBo.CampaignCouponTemplateBo();
            campaignCouponTemplateBo.setCampaignNo(x.getCampaignNo());
            campaignCouponTemplateBo.setCouponTemplateNo(x.getCouponTemplateNo());
            return campaignCouponTemplateBo;
        }).toList();
        return campaignCouponTemplateBoList;
    }
}
