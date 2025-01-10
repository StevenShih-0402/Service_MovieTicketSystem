package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormCouponTemplateFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormCouponTemplateFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignFormCouponTemplateFormEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignFormCouponTemplateFormRepository;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class CampaignFormCouponTemplateFormDao {
    private final String CLASS_NAME = CampaignFormCouponTemplateFormDao.class.getSimpleName();
    private final CampaignFormCouponTemplateFormRepository campaignFormCouponTemplateRepository;

    @Transactional(readOnly = true)
    public List<CampaignFormCouponTemplateFormDto> queryCampaignFormCouponTemplateForm(QueryCampaignFormCouponTemplateFormConditionDto queryCampaignFormCouponTemplateFormConditionDto) {
        List<CampaignFormCouponTemplateFormDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignFormCouponTemplateFormConditionDto)) {
            return reDataList;
        }
        List<CampaignFormCouponTemplateFormEntity> campaignFormCouponTemplateFormEntities = campaignFormCouponTemplateRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(queryCampaignFormCouponTemplateFormConditionDto.getCampaignFormNo())) {
                predicates.add(builder.equal(root.get("campaignFormNo"), queryCampaignFormCouponTemplateFormConditionDto.getCampaignFormNo()));
            }

            if (StringUtils.isNotBlank(queryCampaignFormCouponTemplateFormConditionDto.getCampaignNo())) {
                predicates.add(builder.equal(root.get("campaignNo"), queryCampaignFormCouponTemplateFormConditionDto.getCampaignNo()));
            }

            if (StringUtils.isNotBlank(queryCampaignFormCouponTemplateFormConditionDto.getCouponTemplateFormNo())) {
                predicates.add(builder.equal(root.get("couponTemplateFormNo"), queryCampaignFormCouponTemplateFormConditionDto.getCouponTemplateFormNo()));
            }

            if (StringUtils.isNotBlank(queryCampaignFormCouponTemplateFormConditionDto.getCouponTemplateNo())) {
                predicates.add(builder.equal(root.get("couponTemplateNo"), queryCampaignFormCouponTemplateFormConditionDto.getCouponTemplateNo()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });
        ListUtils.emptyIfNull(campaignFormCouponTemplateFormEntities).forEach(entity -> {
            CampaignFormCouponTemplateFormDto dto = new CampaignFormCouponTemplateFormDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    public CampaignFormCouponTemplateFormDto saveCampaignFormCouponTemplateForm(CampaignFormCouponTemplateFormDto campaignFormCouponTemplateFormDto) {
        CampaignFormCouponTemplateFormDto reCampaignFormCouponTemplateFormDto = new CampaignFormCouponTemplateFormDto();
        CampaignFormCouponTemplateFormEntity campaignFormCouponTemplateFormEntity = new CampaignFormCouponTemplateFormEntity();
        BeanUtils.copyProperties(campaignFormCouponTemplateFormDto, campaignFormCouponTemplateFormEntity);
        campaignFormCouponTemplateRepository.save(campaignFormCouponTemplateFormEntity);
        BeanUtils.copyProperties(campaignFormCouponTemplateFormEntity, reCampaignFormCouponTemplateFormDto);
        return reCampaignFormCouponTemplateFormDto;
    }
}
