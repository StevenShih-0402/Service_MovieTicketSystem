package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.campaignparticipantlist.CampaignParticipantListDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignparticipantlist.QueryCampaignParticipantListConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignParticipantListEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignParticipantListRepository;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class CampaignParticipantListDao {
    private final String CLASS_NAME = CampaignParticipantListDao.class.getSimpleName();
    private final CampaignParticipantListRepository campaignParticipantListRepository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<CampaignParticipantListDto> queryCampaignParticipantList(QueryCampaignParticipantListConditionDto queryCampaignParticipantListConditionDto) {
        List<CampaignParticipantListDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignParticipantListConditionDto)) {
            return reDataList;
        }
        List<CampaignParticipantListEntity> campaignParticipantListEntities = campaignParticipantListRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(queryCampaignParticipantListConditionDto.getCampaignNo())) {
                predicates.add(builder.equal(root.get("campaignNo"), queryCampaignParticipantListConditionDto.getCampaignNo()));
            }

            if (!CollectionUtils.isEmpty(queryCampaignParticipantListConditionDto.getCampaignNoList())) {
                predicates.add(builder.in(root.get("campaignNo")).value(queryCampaignParticipantListConditionDto.getCampaignNoList()));
            }

            if (!CollectionUtils.isEmpty(queryCampaignParticipantListConditionDto.getParticipantListVersionList())) {
                predicates.add(builder.in(root.get("participantListVersion")).value(queryCampaignParticipantListConditionDto.getParticipantListVersionList()));
            }

            if (ObjectUtils.isNotEmpty(queryCampaignParticipantListConditionDto.getIpNo())) {
                predicates.add(builder.equal(root.get("ipNo"), queryCampaignParticipantListConditionDto.getIpNo()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        ListUtils.emptyIfNull(campaignParticipantListEntities).forEach(entity -> {
            CampaignParticipantListDto dto = new CampaignParticipantListDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    @Transactional
    public int deleteByCampaignNo(String campaignNo) {
        return campaignParticipantListRepository.deleteByCampaignNo(campaignNo);
    }

    @Transactional
    public int deleteByParticipantListVersion(String participantListVersion) {
        return campaignParticipantListRepository.deleteByParticipantListVersion(participantListVersion);
    }

    public CampaignParticipantListDto saveCampaignParticipantList(CampaignParticipantListDto campaignParticipantListDto) {
        CampaignParticipantListDto reCampaignParticipantListDto = new CampaignParticipantListDto();
        CampaignParticipantListEntity campaignParticipantListEntity = new CampaignParticipantListEntity();
        BeanUtils.copyProperties(campaignParticipantListDto, campaignParticipantListEntity);
        campaignParticipantListRepository.save(campaignParticipantListEntity);
        BeanUtils.copyProperties(campaignParticipantListEntity, reCampaignParticipantListDto);
        return reCampaignParticipantListDto;
    }

    @Transactional
    public void saveCampaignParticipantList(List<CampaignParticipantListDto> campaignParticipantListDtoList, int batchSize) {
        if (CollectionUtils.isEmpty(campaignParticipantListDtoList)) {
            return;
        }

        String sql = """
                INSERT INTO TB_CAMPAIGN_PARTICIPANT_LIST 
                ( ID
                , CREATE_DTTM
                , UPDATE_DTTM
                , CAMPAIGN_NO
                , IP_NO
                , SOURCE_TYPE
                , CUSTOMER_LIST_NO
                , PARTICIPANT_LIST_VERSION
                )
                VALUES
                ( SEQ_CAMPAIGN_PARTICIPANT_LIST.NEXTVAL
                , SYSTIMESTAMP
                , SYSTIMESTAMP
                , ?
                , ?
                , ?
                , ?
                , ?
                )
                """;

        jdbcTemplate.batchUpdate(sql, campaignParticipantListDtoList, batchSize, (ps, argument) -> {
            ps.setString(1, argument.getCampaignNo());
            ps.setLong(2, argument.getIpNo().longValue());
            ps.setString(3, argument.getSourceType().getCode());
            ps.setString(4, argument.getCustomerListNo());
            ps.setString(5, argument.getParticipantListVersion());
        });
    }

}
