package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignCouponRequestFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignCouponRequestFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignCouponRequestFormEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignCouponRequestFormRepository;
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
public class CampaignCouponRequestFormDao {
    private final String CLASS_NAME = CampaignCouponRequestFormDao.class.getSimpleName();
    private final CampaignCouponRequestFormRepository campaignCouponRequestFormRepository;

    @Transactional(readOnly = true)
    public List<CampaignCouponRequestFormDto> queryCampaignCouponRequestForm(QueryCampaignCouponRequestFormConditionDto queryCampaignCouponRequestFormConditionDto) {
        List<CampaignCouponRequestFormDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignCouponRequestFormConditionDto)) {
            return reDataList;
        }

        List<CampaignCouponRequestFormEntity> CampaignCouponRequestFormEntities = campaignCouponRequestFormRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(queryCampaignCouponRequestFormConditionDto.getCampaignNo())) {
                predicates.add(builder.equal(root.get("campaignNo"), queryCampaignCouponRequestFormConditionDto.getCampaignNo()));
            }

            if (StringUtils.isNotBlank(queryCampaignCouponRequestFormConditionDto.getCouponTemplateNo())) {
                predicates.add(builder.equal(root.get("couponTemplateNo"), queryCampaignCouponRequestFormConditionDto.getCouponTemplateNo()));
            }

            if (StringUtils.isNotBlank(queryCampaignCouponRequestFormConditionDto.getCouponRequestFormNo())) {
                predicates.add(builder.equal(root.get("couponRequestFormNo"), queryCampaignCouponRequestFormConditionDto.getCouponRequestFormNo()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });
        ListUtils.emptyIfNull(CampaignCouponRequestFormEntities).forEach(entity -> {
            CampaignCouponRequestFormDto dto = new CampaignCouponRequestFormDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    public CampaignCouponRequestFormDto saveCampaignCouponRequestForm(CampaignCouponRequestFormDto campaignCouponRequestFormDto) {
        CampaignCouponRequestFormDto reCampaignCouponRequestFormDto = new CampaignCouponRequestFormDto();
        CampaignCouponRequestFormEntity campaignCouponRequestFormEntity = new CampaignCouponRequestFormEntity();
        BeanUtils.copyProperties(campaignCouponRequestFormDto, campaignCouponRequestFormEntity);
        campaignCouponRequestFormRepository.save(campaignCouponRequestFormEntity);
        BeanUtils.copyProperties(campaignCouponRequestFormEntity, reCampaignCouponRequestFormDto);
        return reCampaignCouponRequestFormDto;
    }
}
