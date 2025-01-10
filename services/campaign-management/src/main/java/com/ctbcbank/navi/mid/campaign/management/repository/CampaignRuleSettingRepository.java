package com.ctbcbank.navi.mid.campaign.management.repository;

import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleSettingEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CampaignRuleSettingRepository extends BaseRepository<CampaignRuleSettingEntity, BigInteger> {
    List<CampaignRuleSettingEntity> findByRuleGroupId(BigInteger ruleGroupId);

    @Modifying
    @Query(
            value = """
                    DELETE FROM TB_CAMPAIGN_RULE_SETTING WHERE CAMPAIGN_ID = :campaignId
                    """, nativeQuery = true
    )
    int deleteByCampaignId(BigInteger campaignId);
}
