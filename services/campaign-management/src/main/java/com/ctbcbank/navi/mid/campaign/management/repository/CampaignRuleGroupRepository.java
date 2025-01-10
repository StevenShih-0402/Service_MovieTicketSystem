package com.ctbcbank.navi.mid.campaign.management.repository;

import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleGroupEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CampaignRuleGroupRepository extends BaseRepository<CampaignRuleGroupEntity, BigInteger> {
    List<CampaignRuleGroupEntity> findByCampaignIdOrderById(BigInteger campaignId);

    @Modifying
    @Query(
            value = """
                    DELETE FROM TB_CAMPAIGN_RULE_GROUP WHERE CAMPAIGN_ID = :campaignId
                    """, nativeQuery = true
    )
    int deleteByCampaignId(BigInteger campaignId);
}
