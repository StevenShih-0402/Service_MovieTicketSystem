package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormCommentDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormCommentConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignFormCommentEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignFormCommentRepository;
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
public class CampaignFormCommentDao {
    private final String CLASS_NAME = CampaignFormCommentDao.class.getSimpleName();
    private final CampaignFormCommentRepository campaignFormCommentRepository;

    @Transactional(readOnly = true)
    public List<CampaignFormCommentDto> queryCampaignFormComment(QueryCampaignFormCommentConditionDto queryCampaignFormCommentConditionDto) {
        List<CampaignFormCommentDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignFormCommentConditionDto)) {
            return reDataList;
        }
        List<CampaignFormCommentEntity> campaignFormCommentEntities = campaignFormCommentRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!CollectionUtils.isEmpty(queryCampaignFormCommentConditionDto.getCampaignFormNoList())) {
                predicates.add(builder.in(root.get("campaignFormNo")).value(queryCampaignFormCommentConditionDto.getCampaignFormNoList()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });
        ListUtils.emptyIfNull(campaignFormCommentEntities).forEach(entity -> {
            CampaignFormCommentDto dto = new CampaignFormCommentDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    public CampaignFormCommentDto saveCampaignFormComment(CampaignFormCommentDto campaignFormCommentDto) {
        CampaignFormCommentDto reCampaignFormCommentDto = new CampaignFormCommentDto();
        CampaignFormCommentEntity campaignFormCommentEntity = new CampaignFormCommentEntity();
        BeanUtils.copyProperties(campaignFormCommentDto, campaignFormCommentEntity);
        campaignFormCommentRepository.save(campaignFormCommentEntity);
        BeanUtils.copyProperties(campaignFormCommentEntity, reCampaignFormCommentDto);
        return reCampaignFormCommentDto;
    }

}
