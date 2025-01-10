package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleSettingExtraDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignRuleSettingExtraConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleSettingExtraEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleSettingExtraRepository;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
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
public class CampaignRuleSettingExtraDao {
    private final CampaignRuleSettingExtraRepository campaignRuleSettingExtraRepository;

    public List<CampaignRuleSettingExtraDto> queryCampaignRuleSettingExtra(QueryCampaignRuleSettingExtraConditionDto queryCampaignRuleSettingExtraConditionDto) {
        List<CampaignRuleSettingExtraDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignRuleSettingExtraConditionDto)) {
            return reDataList;
        }

        List<CampaignRuleSettingExtraEntity> campaignRuleSettingExtraEntities = campaignRuleSettingExtraRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(queryCampaignRuleSettingExtraConditionDto.getRuleGroupId())) {
                predicates.add(builder.equal(root.get("ruleGroupId"), queryCampaignRuleSettingExtraConditionDto.getRuleGroupId()));
            }

            if (ObjectUtils.isNotEmpty(queryCampaignRuleSettingExtraConditionDto.getTransactionCode())) {
                predicates.add(builder.equal(root.get("transactionCode"), queryCampaignRuleSettingExtraConditionDto.getTransactionCode()));
            }

            if (!CollectionUtils.isEmpty(queryCampaignRuleSettingExtraConditionDto.getCampaignIdList())) {
                predicates.add(builder.in(root.get("campaignId")).value(queryCampaignRuleSettingExtraConditionDto.getCampaignIdList()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        ListUtils.emptyIfNull(campaignRuleSettingExtraEntities).forEach(entity -> {
            CampaignRuleSettingExtraDto dto = new CampaignRuleSettingExtraDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });

        return reDataList;
    }


    public CampaignRuleSettingExtraDto saveCampaignRuleSettingExtra(CampaignRuleSettingExtraDto campaignRuleSettingExtraDto) {
        CampaignRuleSettingExtraDto reCampaignRuleSettingExtraDto = new CampaignRuleSettingExtraDto();
        CampaignRuleSettingExtraEntity campaignRuleSettingExtraEntity = new CampaignRuleSettingExtraEntity();
        BeanUtils.copyProperties(campaignRuleSettingExtraDto, campaignRuleSettingExtraEntity);
        campaignRuleSettingExtraRepository.save(campaignRuleSettingExtraEntity);
        BeanUtils.copyProperties(campaignRuleSettingExtraEntity, reCampaignRuleSettingExtraDto);
        return reCampaignRuleSettingExtraDto;
    }

    @Transactional
    public int deleteByCampaignId(BigInteger campaignId) {
        return campaignRuleSettingExtraRepository.deleteByCampaignId(campaignId);
    }

}
