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
@Table(name = "TB_CAMPAIGN_RULE_GROUP_COUPON")
public class CampaignRuleGroupCouponEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CAMPAIGN_ID", nullable = false)
    private BigInteger campaignId;

    @Column(name = "RULE_GROUP_ID", nullable = false)
    private BigInteger ruleGroupId;

    @Column(name = "COUPON_TEMPLATE_NO", nullable = false)
    private String couponTemplateNo;

}
