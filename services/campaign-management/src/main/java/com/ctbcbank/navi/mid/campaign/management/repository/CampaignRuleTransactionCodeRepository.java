package com.ctbcbank.navi.mid.campaign.management.repository;

import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleTransactionCodeEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CampaignRuleTransactionCodeRepository extends BaseRepository<CampaignRuleTransactionCodeEntity, BigInteger> {
    @Modifying
    @Query(
            value = """
                    DELETE FROM TB_CAMPAIGN_RULE_TRANSACTION_CODE WHERE CAMPAIGN_ID = :campaignId
                    """, nativeQuery = true
    )
    int deleteByCampaignId(BigInteger campaignId);
}
