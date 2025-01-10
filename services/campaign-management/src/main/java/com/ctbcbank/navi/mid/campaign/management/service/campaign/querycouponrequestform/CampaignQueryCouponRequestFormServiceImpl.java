package com.ctbcbank.navi.mid.campaign.management.service.campaign.querycouponrequestform;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCouponRequestFormDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignCouponRequestFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignCouponRequestFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.createcouponrequestform.CampaignCreateCouponRequestFormServiceImpl;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignQueryCouponRequestFormServiceImpl implements CampaignQueryCouponRequestFormService {
    private final String CLASS_NAME = CampaignQueryCouponRequestFormServiceImpl.class.getSimpleName();
    private final CampaignCouponRequestFormDao campaignCouponRequestFormDao;

    @Override
    public CampaignQueryCouponRequestFormRsBo queryCouponRequestForm(CampaignQueryCouponRequestFormRqBo campaignQueryCouponRequestFormRqBo) {
        QueryCampaignCouponRequestFormConditionDto queryCampaignCouponRequestFormConditionDto = new QueryCampaignCouponRequestFormConditionDto();
        queryCampaignCouponRequestFormConditionDto.setCampaignNo(campaignQueryCouponRequestFormRqBo.getCampaignNo());
        queryCampaignCouponRequestFormConditionDto.setCouponTemplateNo(campaignQueryCouponRequestFormRqBo.getCouponTemplateNo());
        queryCampaignCouponRequestFormConditionDto.setCouponRequestFormNo(campaignQueryCouponRequestFormRqBo.getCouponRequestFormNo());
        List<CampaignCouponRequestFormDto> campaignCouponRequestFormDtoList = campaignCouponRequestFormDao.queryCampaignCouponRequestForm(queryCampaignCouponRequestFormConditionDto);
        CampaignQueryCouponRequestFormRsBo campaignQueryCouponRequestFormRsBo = new CampaignQueryCouponRequestFormRsBo();
        campaignQueryCouponRequestFormRsBo.setCampaignCouponRequestFormBoList(getCampaignCouponRequestFormBoList(campaignCouponRequestFormDtoList));
        return campaignQueryCouponRequestFormRsBo;
    }

    private List<CampaignQueryCouponRequestFormRsBo.CampaignCouponRequestFormBo> getCampaignCouponRequestFormBoList(List<CampaignCouponRequestFormDto> campaignCouponRequestFormDtoList) {
        List<CampaignQueryCouponRequestFormRsBo.CampaignCouponRequestFormBo> campaignCouponRequestFormBoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignCouponRequestFormDtoList)) {
            return campaignCouponRequestFormBoList;
        }
        campaignCouponRequestFormBoList = campaignCouponRequestFormDtoList.stream().map(x -> {
            CampaignQueryCouponRequestFormRsBo.CampaignCouponRequestFormBo campaignCouponRequestFormBo = new CampaignQueryCouponRequestFormRsBo.CampaignCouponRequestFormBo();
            campaignCouponRequestFormBo.setCampaignNo(x.getCampaignNo());
            campaignCouponRequestFormBo.setCouponTemplateNo(x.getCouponTemplateNo());
            campaignCouponRequestFormBo.setCouponRequestFormNo(x.getCouponRequestFormNo());
            return campaignCouponRequestFormBo;
        }).toList();
        return campaignCouponRequestFormBoList;
    }

}
