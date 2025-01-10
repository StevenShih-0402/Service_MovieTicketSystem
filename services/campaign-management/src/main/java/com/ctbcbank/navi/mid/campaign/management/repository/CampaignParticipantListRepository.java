package com.ctbcbank.navi.mid.campaign.management.repository;

import com.ctbcbank.navi.mid.campaign.management.entity.CampaignParticipantListEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface CampaignParticipantListRepository extends BaseRepository<CampaignParticipantListEntity, BigInteger> {

    @Modifying
    @Query(
            value = """
                    DELETE FROM TB_CAMPAIGN_PARTICIPANT_LIST WHERE CAMPAIGN_NO = :campaignNo
                    """, nativeQuery = true
    )
    int deleteByCampaignNo(String campaignNo);

    @Modifying
    @Query(
            value = """
                    DELETE FROM TB_CAMPAIGN_PARTICIPANT_LIST WHERE PARTICIPANT_LIST_VERSION = :participantListVersion
                    """, nativeQuery = true
    )
    int deleteByParticipantListVersion(String participantListVersion);

}
