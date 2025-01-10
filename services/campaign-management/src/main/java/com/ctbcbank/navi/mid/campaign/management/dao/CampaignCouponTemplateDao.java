package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignCouponTemplateDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignCouponTemplateConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignCouponTemplateEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignCouponTemplateRepository;
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
public class CampaignCouponTemplateDao {
    private final String CLASS_NAME = CampaignCouponTemplateDao.class.getSimpleName();
    private final CampaignCouponTemplateRepository campaignCouponTemplateRepository;

    @Transactional(readOnly = true)
    public List<CampaignCouponTemplateDto> queryCampaignCouponTemplate(QueryCampaignCouponTemplateConditionDto queryCampaignCouponTemplateConditionDto) {
        List<CampaignCouponTemplateDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignCouponTemplateConditionDto)) {
            return reDataList;
        }
        List<CampaignCouponTemplateEntity> campaignCouponTemplateEntities = campaignCouponTemplateRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(queryCampaignCouponTemplateConditionDto.getCampaignNo())) {
                predicates.add(builder.equal(root.get("campaignNo"), queryCampaignCouponTemplateConditionDto.getCampaignNo()));
            }

            if (StringUtils.isNotBlank(queryCampaignCouponTemplateConditionDto.getCouponTemplateNo())) {
                predicates.add(builder.equal(root.get("couponTemplateNo"), queryCampaignCouponTemplateConditionDto.getCouponTemplateNo()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        ListUtils.emptyIfNull(campaignCouponTemplateEntities).forEach(entity -> {
            CampaignCouponTemplateDto dto = new CampaignCouponTemplateDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    public CampaignCouponTemplateDto saveCampaignCouponTemplate(CampaignCouponTemplateDto campaignCouponTemplateDto) {
        CampaignCouponTemplateDto reCampaignCouponTemplateDto = new CampaignCouponTemplateDto();
        CampaignCouponTemplateEntity campaignCouponTemplateEntity = new CampaignCouponTemplateEntity();
        BeanUtils.copyProperties(campaignCouponTemplateDto, campaignCouponTemplateEntity);
        campaignCouponTemplateRepository.save(campaignCouponTemplateEntity);
        BeanUtils.copyProperties(campaignCouponTemplateEntity, reCampaignCouponTemplateDto);
        return reCampaignCouponTemplateDto;
    }
}
