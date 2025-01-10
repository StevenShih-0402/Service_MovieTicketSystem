package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleExtraDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignRuleExtraConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleExtraEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleExtraRepository;
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
public class CampaignRuleExtraDao {
    private final CampaignRuleExtraRepository campaignRuleExtraRepository;

    @Transactional(readOnly = true)
    public List<CampaignRuleExtraDto> queryCampaignRuleExtra(QueryCampaignRuleExtraConditionDto queryCampaignRuleExtraConditionDto) {
        List<CampaignRuleExtraDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignRuleExtraConditionDto)) {
            return reDataList;
        }
        List<CampaignRuleExtraEntity> campaignRuleExtraEntities = campaignRuleExtraRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(queryCampaignRuleExtraConditionDto.getTransactionCode())) {
                predicates.add(builder.equal(root.get("transactionCode"), queryCampaignRuleExtraConditionDto.getTransactionCode()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        ListUtils.emptyIfNull(campaignRuleExtraEntities).forEach(entity -> {
            CampaignRuleExtraDto dto = new CampaignRuleExtraDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });

        return reDataList;
    }
}
