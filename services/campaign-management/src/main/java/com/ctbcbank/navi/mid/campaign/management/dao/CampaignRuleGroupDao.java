package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleGroupDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignRuleGroupConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleGroupEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleGroupRepository;
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
public class CampaignRuleGroupDao {
    private final CampaignRuleGroupRepository campaignRuleGroupRepository;

    @Transactional(readOnly = true)
    public List<CampaignRuleGroupDto> queryCampaignRuleGroup(QueryCampaignRuleGroupConditionDto queryCampaignRuleGroupConditionDto) {
        List<CampaignRuleGroupDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignRuleGroupConditionDto)) {
            return reDataList;
        }
        List<CampaignRuleGroupEntity> campaignRuleGroupEntities = campaignRuleGroupRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(queryCampaignRuleGroupConditionDto.getCampaignId())) {
                predicates.add(builder.equal(root.get("campaignId"), queryCampaignRuleGroupConditionDto.getCampaignId()));
            }

            if (ObjectUtils.isNotEmpty(queryCampaignRuleGroupConditionDto.getRuleGroupParentId())) {
                predicates.add(builder.equal(root.get("ruleGroupParentId"), queryCampaignRuleGroupConditionDto.getRuleGroupParentId()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });
        ListUtils.emptyIfNull(campaignRuleGroupEntities).forEach(entity -> {
            CampaignRuleGroupDto dto = new CampaignRuleGroupDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    @Transactional
    public int deleteByCampaignId(BigInteger campaignId) {
        return campaignRuleGroupRepository.deleteByCampaignId(campaignId);
    }
}
