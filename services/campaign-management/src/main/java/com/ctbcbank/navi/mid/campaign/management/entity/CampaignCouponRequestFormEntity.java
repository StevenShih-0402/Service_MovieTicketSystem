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
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "TB_CAMPAIGN_COUPON_REQUEST_FORM")
public class CampaignCouponRequestFormEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CAMPAIGN_NO", nullable = false)
    private String campaignNo;

    @Column(name = "COUPON_TEMPLATE_NO", nullable = false)
    private String couponTemplateNo;

    @Column(name = "COUPON_REQUEST_FORM_NO", nullable = false)
    private String couponRequestFormNo;

}
