package com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcoupontemplateform;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormCouponTemplateFormDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormCouponTemplateFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormCouponTemplateFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.querycoupontemplateform.CampaignFormQueryCouponTemplateFormRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.querycoupontemplateform.CampaignFormQueryCouponTemplateFormServiceImpl;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormCreateCouponTemplateFormServiceImpl implements CampaignFormCreateCouponTemplateFormService {
    private final String CLASS_NAME = CampaignFormCreateCouponTemplateFormServiceImpl.class.getSimpleName();
    private final CampaignFormCouponTemplateFormDao campaignFormCouponTemplateFormDao;

    @Override
    public CampaignFormCreateCouponTemplateFormRsBo createCouponTemplateForm(CampaignFormCreateCouponTemplateFormRqBo campaignFormCreateCouponTemplateFormRqBo) {
        CampaignFormCreateCouponTemplateFormRsBo campaignFormCreateCouponTemplateFormRsBo = new CampaignFormCreateCouponTemplateFormRsBo();
        QueryCampaignFormCouponTemplateFormConditionDto queryCampaignFormCouponTemplateFormConditionDto = getQueryCampaignFormCouponTemplateFormConditionDto(campaignFormCreateCouponTemplateFormRqBo);
        List<CampaignFormCouponTemplateFormDto> campaignFormCouponTemplateFormDtoList = campaignFormCouponTemplateFormDao.queryCampaignFormCouponTemplateForm(
                queryCampaignFormCouponTemplateFormConditionDto);
        if (!CollectionUtils.isEmpty(campaignFormCouponTemplateFormDtoList)) {
            CampaignFormCouponTemplateFormDto campaignFormCouponTemplateFormDto = campaignFormCouponTemplateFormDtoList.get(0);
            campaignFormCreateCouponTemplateFormRsBo.setCampaignFormNo(campaignFormCouponTemplateFormDto.getCampaignFormNo());
            campaignFormCreateCouponTemplateFormRsBo.setCampaignNo(campaignFormCouponTemplateFormDto.getCampaignNo());
            campaignFormCreateCouponTemplateFormRsBo.setCouponTemplateFormNo(campaignFormCouponTemplateFormDto.getCouponTemplateFormNo());
            campaignFormCreateCouponTemplateFormRsBo.setCouponTemplateNo(campaignFormCouponTemplateFormDto.getCouponTemplateNo());
            return campaignFormCreateCouponTemplateFormRsBo;
        }
        CampaignFormCouponTemplateFormDto campaignFormCouponTemplateFormDto = new CampaignFormCouponTemplateFormDto();
        campaignFormCouponTemplateFormDto.setCampaignFormNo(campaignFormCreateCouponTemplateFormRqBo.getCampaignFormNo());
        campaignFormCouponTemplateFormDto.setCampaignNo(campaignFormCreateCouponTemplateFormRqBo.getCampaignNo());
        campaignFormCouponTemplateFormDto.setCouponTemplateFormNo(campaignFormCreateCouponTemplateFormRqBo.getCouponTemplateFormNo());
        campaignFormCouponTemplateFormDto.setCouponTemplateNo(campaignFormCreateCouponTemplateFormRqBo.getCouponTemplateNo());
        CampaignFormCouponTemplateFormDto saveCampaignFormCouponTemplateFormDto = campaignFormCouponTemplateFormDao.saveCampaignFormCouponTemplateForm(campaignFormCouponTemplateFormDto);
        campaignFormCreateCouponTemplateFormRsBo.setCampaignFormNo(saveCampaignFormCouponTemplateFormDto.getCampaignFormNo());
        campaignFormCreateCouponTemplateFormRsBo.setCampaignNo(saveCampaignFormCouponTemplateFormDto.getCampaignNo());
        campaignFormCreateCouponTemplateFormRsBo.setCouponTemplateFormNo(saveCampaignFormCouponTemplateFormDto.getCouponTemplateFormNo());
        campaignFormCreateCouponTemplateFormRsBo.setCouponTemplateNo(saveCampaignFormCouponTemplateFormDto.getCouponTemplateNo());
        return campaignFormCreateCouponTemplateFormRsBo;
    }

    private QueryCampaignFormCouponTemplateFormConditionDto getQueryCampaignFormCouponTemplateFormConditionDto(CampaignFormCreateCouponTemplateFormRqBo campaignFormCreateCouponTemplateFormRqBo) {
        QueryCampaignFormCouponTemplateFormConditionDto queryCampaignFormCouponTemplateFormConditionDto = new QueryCampaignFormCouponTemplateFormConditionDto();
        if (StringUtils.isNotBlank(campaignFormCreateCouponTemplateFormRqBo.getCampaignFormNo())) {
            queryCampaignFormCouponTemplateFormConditionDto.setCampaignFormNo(campaignFormCreateCouponTemplateFormRqBo.getCampaignFormNo());
        }
        if (StringUtils.isNotBlank(campaignFormCreateCouponTemplateFormRqBo.getCampaignNo())) {
            queryCampaignFormCouponTemplateFormConditionDto.setCampaignNo(campaignFormCreateCouponTemplateFormRqBo.getCampaignNo());
        }
        if (StringUtils.isNotBlank(campaignFormCreateCouponTemplateFormRqBo.getCouponTemplateFormNo())) {
            queryCampaignFormCouponTemplateFormConditionDto.setCouponTemplateFormNo(campaignFormCreateCouponTemplateFormRqBo.getCouponTemplateFormNo());
        }
        if (StringUtils.isNotBlank(campaignFormCreateCouponTemplateFormRqBo.getCouponTemplateNo())) {
            queryCampaignFormCouponTemplateFormConditionDto.setCouponTemplateNo(campaignFormCreateCouponTemplateFormRqBo.getCouponTemplateNo());
        }
        return queryCampaignFormCouponTemplateFormConditionDto;
    }
}
