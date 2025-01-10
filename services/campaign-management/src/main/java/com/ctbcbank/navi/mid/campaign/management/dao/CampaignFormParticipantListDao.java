package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.campaignformparticipantlist.CampaignFormParticipantListDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformparticipantlist.QueryCampaignFormParticipantListConditionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignFormParticipantListEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignFormParticipantListRepository;
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
import java.util.Collection;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class CampaignFormParticipantListDao {
    private final String CLASS_NAME = CampaignFormParticipantListDao.class.getSimpleName();
    private final CampaignFormParticipantListRepository campaignFormParticipantListRepository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<CampaignFormParticipantListDto> queryCampaignFormParticipantList(QueryCampaignFormParticipantListConditionDto queryCampaignFormParticipantListConditionDto) {
        List<CampaignFormParticipantListDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(queryCampaignFormParticipantListConditionDto)) {
            return reDataList;
        }
        List<CampaignFormParticipantListEntity> campaignFormParticipantListEntities = campaignFormParticipantListRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(queryCampaignFormParticipantListConditionDto.getCampaignFormNo())) {
                predicates.add(builder.equal(root.get("campaignFormNo"), queryCampaignFormParticipantListConditionDto.getCampaignFormNo()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });
        ListUtils.emptyIfNull(campaignFormParticipantListEntities).forEach(entity -> {
            CampaignFormParticipantListDto dto = new CampaignFormParticipantListDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    @Transactional
    public int deleteByCampaignFormNo(String campaignFormNo) {
        return campaignFormParticipantListRepository.deleteByCampaignFormNo(campaignFormNo);
    }


    @Transactional
    public void saveCampaignFormParticipantList(List<CampaignFormParticipantListDto> campaignFormParticipantListDtoList, int batchSize) {
        if (CollectionUtils.isEmpty(campaignFormParticipantListDtoList)) {
            return;
        }
        String sql = """
                INSERT INTO TB_CAMPAIGN_FORM_PARTICIPANT_LIST 
                ( ID
                , CREATE_DTTM
                , UPDATE_DTTM
                , CAMPAIGN_FORM_NO
                , CAMPAIGN_NO
                , ID_NO
                , CIF_NO
                , IP_NO
                )
                VALUES
                ( SEQ_CAMPAIGN_FORM_PARTICIPANT_LIST.NEXTVAL
                , SYSTIMESTAMP
                , SYSTIMESTAMP
                , ?
                , ?
                , ?
                , ?
                , ?
                )
                """;
        jdbcTemplate.batchUpdate(sql, campaignFormParticipantListDtoList, batchSize, (ps, argument) -> {
            ps.setString(1, argument.getCampaignFormNo());
            ps.setString(2, argument.getCampaignNo());
            ps.setString(3, argument.getIdNo());
            ps.setString(4, argument.getCifNo());
            ps.setLong(5, argument.getIpNo().longValue());
        });
    }

}
