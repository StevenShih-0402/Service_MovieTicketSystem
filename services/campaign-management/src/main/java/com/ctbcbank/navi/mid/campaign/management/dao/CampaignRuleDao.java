package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignRuleConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleRepository;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
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
public class CampaignRuleDao {
    private final CampaignRuleRepository campaignRuleRepository;

    @Transactional(readOnly = true)
    public List<CampaignRuleDto> queryCampaignRule(QueryCampaignRuleConditionDto queryCampaignRuleConditionDto) {
        List<CampaignRuleDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignRuleConditionDto)) {
            return reDataList;
        }
        List<CampaignRuleEntity> campaignRuleEntities = campaignRuleRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(queryCampaignRuleConditionDto.getTransactionCode())) {
                predicates.add(builder.equal(root.get("transactionCode"), queryCampaignRuleConditionDto.getTransactionCode()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        ListUtils.emptyIfNull(campaignRuleEntities).forEach(entity -> {
            CampaignRuleDto dto = new CampaignRuleDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });

        return reDataList;
    }
}
