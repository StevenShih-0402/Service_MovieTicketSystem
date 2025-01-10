package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlist.CampaignCustomerListDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlist.QueryCampaignCustomerListConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignCustomerListEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignCustomerListRepository;
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
public class CampaignCustomerListDao {
    private final String CLASS_NAME = CampaignCustomerListDao.class.getSimpleName();
    private final CampaignCustomerListRepository campaignCustomerListRepository;


    @Transactional(readOnly = true)
    public List<CampaignCustomerListDto> queryCampaignCustomerList(QueryCampaignCustomerListConditionDto queryCampaignCustomerListConditionDto) {
        List<CampaignCustomerListDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignCustomerListConditionDto)) {
            return reDataList;
        }
        List<CampaignCustomerListEntity> campaignCustomerListEntities = campaignCustomerListRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!CollectionUtils.isEmpty(queryCampaignCustomerListConditionDto.getStatusList())) {
                predicates.add(builder.in(root.get("status")).value(queryCampaignCustomerListConditionDto.getStatusList()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        ListUtils.emptyIfNull(campaignCustomerListEntities).forEach(entity -> {
            CampaignCustomerListDto dto = new CampaignCustomerListDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });

        return reDataList;
    }

}
