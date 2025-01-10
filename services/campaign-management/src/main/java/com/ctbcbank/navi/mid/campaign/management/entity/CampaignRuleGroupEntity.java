package com.ctbcbank.navi.mid.campaign.management.entity;

import com.ibm.cbmp.fabric.foundation.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "TB_CAMPAIGN_RULE_GROUP")
public class CampaignRuleGroupEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CAMPAIGN_ID", nullable = false)
    private BigInteger campaignId;

    @Column(name = "RULE_GROUP_PARENT_ID", nullable = true)
    private BigInteger ruleGroupParentId;

    @Column(name = "HAS_COUPON", nullable = false)
    private String hasCoupon;

    @Column(name = "MATCH_COUNT", nullable = false)
    private Long matchCount;
}
