package com.ctbcbank.navi.mid.campaign.management.repository;

import com.ctbcbank.navi.mid.campaign.management.entity.CampaignRuleGroupCouponEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CampaignRuleGroupCouponRepository extends BaseRepository<CampaignRuleGroupCouponEntity, BigInteger> {
    List<CampaignRuleGroupCouponEntity> findByRuleGroupId(BigInteger ruleGroupId);

    @Modifying
    @Query(
            value = """
                    DELETE FROM TB_CAMPAIGN_RULE_GROUP_COUPON WHERE CAMPAIGN_ID = :campaignId
                    """, nativeQuery = true
    )
    int deleteByCampaignId(BigInteger campaignId);
}
