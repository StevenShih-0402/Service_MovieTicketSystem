package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignFormEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignFormRepository;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
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
public class CampaignFormDao {
    private final String CLASS_NAME = CampaignFormDao.class.getSimpleName();
    private final CampaignFormRepository campaignFormRepository;

    @Transactional(readOnly = true)
    public List<CampaignFormDto> queryCampaignForm(QueryCampaignFormConditionDto campaignFormConditionDto) {
        List<CampaignFormDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(campaignFormConditionDto)) {
            return reDataList;
        }

        List<CampaignFormEntity> campaignFormEntities = campaignFormRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(campaignFormConditionDto.getCampaignFormNo())) {
                predicates.add(builder.equal(root.get("campaignFormNo"), campaignFormConditionDto.getCampaignFormNo()));
            }

            if(!CollectionUtils.isEmpty(campaignFormConditionDto.getReviewStatusList())){
                predicates.add(builder.in(root.get("reviewStatus")).value(campaignFormConditionDto.getReviewStatusList()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        ListUtils.emptyIfNull(campaignFormEntities).forEach(entity -> {
            CampaignFormDto dto = new CampaignFormDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    public CampaignFormDto saveCampaignForm(CampaignFormDto campaignFormDto) {
        CampaignFormDto reCampaignFormDto = new CampaignFormDto();
        CampaignFormEntity campaignFormEntity = new CampaignFormEntity();
        BeanUtils.copyProperties(campaignFormDto, campaignFormEntity);
        campaignFormRepository.save(campaignFormEntity);
        BeanUtils.copyProperties(campaignFormEntity, reCampaignFormDto);
        return reCampaignFormDto;
    }
}
