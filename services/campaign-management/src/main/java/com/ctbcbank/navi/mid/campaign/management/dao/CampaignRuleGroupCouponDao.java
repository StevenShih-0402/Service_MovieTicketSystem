package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleGroupCouponDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignRuleGroupCouponConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleGroupCouponEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleGroupCouponRepository;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class CampaignRuleGroupCouponDao {
    private final CampaignRuleGroupCouponRepository campaignRuleGroupCouponRepository;

    @Transactional(readOnly = true)
    public List<CampaignRuleGroupCouponDto> queryCampaignRuleGroupCoupon(QueryCampaignRuleGroupCouponConditionDto queryCampaignRuleGroupCouponConditionDto) {
        List<CampaignRuleGroupCouponDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignRuleGroupCouponConditionDto)) {
            return reDataList;
        }
        List<CampaignRuleGroupCouponEntity> campaignRuleGroupCouponEntities = campaignRuleGroupCouponRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(queryCampaignRuleGroupCouponConditionDto.getRuleGroupId())) {
                predicates.add(builder.equal(root.get("ruleGroupId"), queryCampaignRuleGroupCouponConditionDto.getRuleGroupId()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        });
        ListUtils.emptyIfNull(campaignRuleGroupCouponEntities).forEach(entity -> {
            CampaignRuleGroupCouponDto dto = new CampaignRuleGroupCouponDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    public CampaignRuleGroupCouponDto saveCampaignRuleGroupCoupon(CampaignRuleGroupCouponDto campaignRuleGroupCouponDto) {
        CampaignRuleGroupCouponDto reCampaignRuleGroupCouponDto = new CampaignRuleGroupCouponDto();
        CampaignRuleGroupCouponEntity campaignRuleGroupCouponEntity = new CampaignRuleGroupCouponEntity();
        BeanUtils.copyProperties(campaignRuleGroupCouponDto, campaignRuleGroupCouponEntity);
        campaignRuleGroupCouponRepository.save(campaignRuleGroupCouponEntity);
        BeanUtils.copyProperties(campaignRuleGroupCouponEntity, reCampaignRuleGroupCouponDto);
        return reCampaignRuleGroupCouponDto;
    }

    @Transactional
    public int deleteByCampaignId(BigInteger campaignId) {
        return campaignRuleGroupCouponRepository.deleteByCampaignId(campaignId);
    }
}
