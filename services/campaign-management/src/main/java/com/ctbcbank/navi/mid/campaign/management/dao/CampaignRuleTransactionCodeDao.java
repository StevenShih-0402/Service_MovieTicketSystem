package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleTransactionCodeDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignRuleTransactionCodeConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleTransactionCodeEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRuleTransactionCodeRepository;
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
public class CampaignRuleTransactionCodeDao {
    private final CampaignRuleTransactionCodeRepository campaignRuleTransactionCodeRepository;

    public List<CampaignRuleTransactionCodeDto> queryCampaignRuleTransactionCode(QueryCampaignRuleTransactionCodeConditionDto campaignRuleTransactionCodeConditionDto) {
        List<CampaignRuleTransactionCodeDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(campaignRuleTransactionCodeConditionDto)) {
            return reDataList;
        }
        List<CampaignRuleTransactionCodeEntity> campaignRuleSettingEntities = campaignRuleTransactionCodeRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!CollectionUtils.isEmpty(campaignRuleTransactionCodeConditionDto.getCampaignIdList())) {
                predicates.add(builder.in(root.get("campaignId")).value(campaignRuleTransactionCodeConditionDto.getCampaignIdList()));
            }

            if (!CollectionUtils.isEmpty(campaignRuleTransactionCodeConditionDto.getTransactionCodeList())) {
                predicates.add(builder.in(root.get("transactionCode")).value(campaignRuleTransactionCodeConditionDto.getTransactionCodeList()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });


        ListUtils.emptyIfNull(campaignRuleSettingEntities).forEach(entity -> {
            CampaignRuleTransactionCodeDto dto = CampaignRuleTransactionCodeDto.builder().build();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    public CampaignRuleTransactionCodeDto saveCampaignRuleTransactionCode(CampaignRuleTransactionCodeDto campaignRuleTransactionCodeDto) {
        CampaignRuleTransactionCodeDto reCampaignRuleTransactionCodeDto = new CampaignRuleTransactionCodeDto();
        CampaignRuleTransactionCodeEntity campaignRuleTransactionCodeEntity = new CampaignRuleTransactionCodeEntity();
        BeanUtils.copyProperties(campaignRuleTransactionCodeDto, campaignRuleTransactionCodeEntity);
        campaignRuleTransactionCodeRepository.save(campaignRuleTransactionCodeEntity);
        BeanUtils.copyProperties(campaignRuleTransactionCodeRepository, reCampaignRuleTransactionCodeDto);
        return reCampaignRuleTransactionCodeDto;
    }

    @Transactional
    public int deleteByCampaignId(BigInteger campaignId) {
        return campaignRuleTransactionCodeRepository.deleteByCampaignId(campaignId);
    }
}
