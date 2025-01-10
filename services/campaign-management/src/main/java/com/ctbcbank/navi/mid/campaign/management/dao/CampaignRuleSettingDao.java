package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleSettingDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignRuleSettingConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleSettingEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleSettingRepository;
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
public class CampaignRuleSettingDao {
    private final CampaignRuleSettingRepository campaignRuleSettingRepository;

    @Transactional(readOnly = true)
    public List<CampaignRuleSettingDto> queryCampaignRuleSetting(QueryCampaignRuleSettingConditionDto queryCampaignRuleSettingConditionDto) {
        List<CampaignRuleSettingDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignRuleSettingConditionDto)) {
            return reDataList;
        }
        List<CampaignRuleSettingEntity> campaignRuleSettingEntities = campaignRuleSettingRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(queryCampaignRuleSettingConditionDto.getRuleGroupId())) {
                predicates.add(builder.equal(root.get("ruleGroupId"), queryCampaignRuleSettingConditionDto.getRuleGroupId()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });
        ListUtils.emptyIfNull(campaignRuleSettingEntities).forEach(entity -> {
            CampaignRuleSettingDto dto = new CampaignRuleSettingDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    public CampaignRuleSettingDto saveCampaignRuleSetting(CampaignRuleSettingDto campaignRuleSettingDto) {
        CampaignRuleSettingDto reCampaignRuleSettingDto = new CampaignRuleSettingDto();
        CampaignRuleSettingEntity campaignRuleSettingEntity = new CampaignRuleSettingEntity();
        BeanUtils.copyProperties(campaignRuleSettingDto, campaignRuleSettingEntity);
        campaignRuleSettingRepository.save(campaignRuleSettingEntity);
        BeanUtils.copyProperties(campaignRuleSettingEntity, reCampaignRuleSettingDto);
        return reCampaignRuleSettingDto;
    }

    @Transactional
    public int deleteByCampaignId(BigInteger campaignId) {
        return campaignRuleSettingRepository.deleteByCampaignId(campaignId);
    }
}
