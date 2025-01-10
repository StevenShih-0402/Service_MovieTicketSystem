package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRoleDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignRoleConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRoleEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignRoleRepository;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class CampaignRoleDao {

    private final CampaignRoleRepository campaignRoleRepository;

    public List<CampaignRoleDto> queryCampaignRole(QueryCampaignRoleConditionDto queryCampaignRoleConditionDto) {
        List<CampaignRoleDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignRoleConditionDto)) {
            return reDataList;
        }

        List<CampaignRoleEntity> campaignRoleEntities = campaignRoleRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ObjectUtils.isNotEmpty(queryCampaignRoleConditionDto.getRoleId())) {
                predicates.add(builder.equal(root.get("id"), queryCampaignRoleConditionDto.getRoleId()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        ListUtils.emptyIfNull(campaignRoleEntities).forEach(entity -> {
            CampaignRoleDto dto = new CampaignRoleDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }
}
