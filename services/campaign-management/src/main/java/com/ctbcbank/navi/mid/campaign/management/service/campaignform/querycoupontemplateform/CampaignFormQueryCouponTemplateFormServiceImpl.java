package com.ctbcbank.navi.mid.campaign.management.service.campaignform.querycoupontemplateform;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormCouponTemplateFormDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormCouponTemplateFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormCouponTemplateFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.querybycampaignformno.CampaignFormQueryByFormNoServiceImpl;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormQueryCouponTemplateFormServiceImpl implements CampaignFormQueryCouponTemplateFormService {
    private final String CLASS_NAME = CampaignFormQueryCouponTemplateFormServiceImpl.class.getSimpleName();
    private final CampaignFormCouponTemplateFormDao campaignFormCouponTemplateFormDao;

    @Override
    public CampaignFormQueryCouponTemplateFormRsBo queryCouponTemplateForm(CampaignFormQueryCouponTemplateFormRqBo campaignFormQueryCouponTemplateFormRqBo) {
        QueryCampaignFormCouponTemplateFormConditionDto queryCampaignFormCouponTemplateFormConditionDto = getQueryCampaignFormCouponTemplateFormConditionDto(campaignFormQueryCouponTemplateFormRqBo);
        List<CampaignFormCouponTemplateFormDto> campaignFormCouponTemplateFormDtoList = campaignFormCouponTemplateFormDao.queryCampaignFormCouponTemplateForm(
                queryCampaignFormCouponTemplateFormConditionDto);
        CampaignFormQueryCouponTemplateFormRsBo campaignFormQueryCouponTemplateFormRsBo = new CampaignFormQueryCouponTemplateFormRsBo();
        campaignFormQueryCouponTemplateFormRsBo.setCampaignFormCouponTemplateFormList(getCampaignFormCouponTemplateFormBoList(campaignFormCouponTemplateFormDtoList));
        return campaignFormQueryCouponTemplateFormRsBo;
    }

    private QueryCampaignFormCouponTemplateFormConditionDto getQueryCampaignFormCouponTemplateFormConditionDto(CampaignFormQueryCouponTemplateFormRqBo campaignFormQueryCouponTemplateFormRqBo) {
        QueryCampaignFormCouponTemplateFormConditionDto queryCampaignFormCouponTemplateFormConditionDto = new QueryCampaignFormCouponTemplateFormConditionDto();
        if (StringUtils.isNotBlank(campaignFormQueryCouponTemplateFormRqBo.getCampaignFormNo())) {
            queryCampaignFormCouponTemplateFormConditionDto.setCampaignFormNo(campaignFormQueryCouponTemplateFormRqBo.getCampaignFormNo());
        }
        if (StringUtils.isNotBlank(campaignFormQueryCouponTemplateFormRqBo.getCouponTemplateFormNo())) {
            queryCampaignFormCouponTemplateFormConditionDto.setCouponTemplateFormNo(campaignFormQueryCouponTemplateFormRqBo.getCouponTemplateFormNo());
        }
        return queryCampaignFormCouponTemplateFormConditionDto;
    }

    private List<CampaignFormQueryCouponTemplateFormRsBo.CampaignFormCouponTemplateFormBo> getCampaignFormCouponTemplateFormBoList(List<CampaignFormCouponTemplateFormDto> campaignFormCouponTemplateFormDtoList) {
        List<CampaignFormQueryCouponTemplateFormRsBo.CampaignFormCouponTemplateFormBo> campaignFormCouponTemplateFormBoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignFormCouponTemplateFormDtoList)) {
            return campaignFormCouponTemplateFormBoList;
        }
        campaignFormCouponTemplateFormBoList = campaignFormCouponTemplateFormDtoList.stream().map(x -> {
            CampaignFormQueryCouponTemplateFormRsBo.CampaignFormCouponTemplateFormBo campaignFormCouponTemplateFormBo = new CampaignFormQueryCouponTemplateFormRsBo.CampaignFormCouponTemplateFormBo();
            campaignFormCouponTemplateFormBo.setCampaignFormNo(x.getCampaignFormNo());
            campaignFormCouponTemplateFormBo.setCouponTemplateFormNo(x.getCouponTemplateFormNo());
            return campaignFormCouponTemplateFormBo;
        }).toList();
        return campaignFormCouponTemplateFormBoList;
    }
}
