package com.ctbcbank.navi.mid.campaign.management.repository;

import com.ctbcbank.navi.mid.campaign.management.entity.CampaignFormParticipantListEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface CampaignFormParticipantListRepository extends BaseRepository<CampaignFormParticipantListEntity, BigInteger> {

    @Modifying
    @Query(
            value = """
                    DELETE FROM TB_CAMPAIGN_FORM_PARTICIPANT_LIST WHERE CAMPAIGN_FORM_NO = :campaignFormNo
                    """, nativeQuery = true
    )
    int deleteByCampaignFormNo(String campaignFormNo);

}
