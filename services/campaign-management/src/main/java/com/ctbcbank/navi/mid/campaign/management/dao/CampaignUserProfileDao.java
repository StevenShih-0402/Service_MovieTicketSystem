package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignUserProfileDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignUserProfileConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignUserProfileEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignUserProfileRepository;
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
public class CampaignUserProfileDao {
    private final CampaignUserProfileRepository campaignUserProfileRepository;

    @Transactional(readOnly = true)
    public List<CampaignUserProfileDto> queryCampaignUserProfile(QueryCampaignUserProfileConditionDto queryCampaignUserProfileConditionDto) {
        List<CampaignUserProfileDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignUserProfileConditionDto)) {
            return reDataList;
        }
        List<CampaignUserProfileEntity> campaignUserProfileEntities = campaignUserProfileRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(queryCampaignUserProfileConditionDto.getToken()) && StringUtils.isNotBlank(queryCampaignUserProfileConditionDto.getToken())) {
                predicates.add(builder.equal(root.get("token"), queryCampaignUserProfileConditionDto.getToken()));
            }

            if (ObjectUtils.isNotEmpty(queryCampaignUserProfileConditionDto.getStatus()) && StringUtils.isNotBlank(queryCampaignUserProfileConditionDto.getStatus())) {
                predicates.add(builder.equal(root.get("status"), queryCampaignUserProfileConditionDto.getStatus()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        ListUtils.emptyIfNull(campaignUserProfileEntities).forEach(entity -> {
            CampaignUserProfileDto dto = new CampaignUserProfileDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }
}
