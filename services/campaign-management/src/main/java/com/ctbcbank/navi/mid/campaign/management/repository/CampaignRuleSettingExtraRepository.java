package com.ctbcbank.navi.mid.campaign.management.repository;

import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleSettingExtraEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CampaignRuleSettingExtraRepository extends BaseRepository<CampaignRuleSettingExtraEntity, BigInteger> {
    @Modifying
    @Query(
            value = """
                    DELETE FROM TB_CAMPAIGN_RULE_SETTING_EXTRA WHERE CAMPAIGN_ID = :campaignId
                    """, nativeQuery = true
    )
    int deleteByCampaignId(BigInteger campaignId);
}
