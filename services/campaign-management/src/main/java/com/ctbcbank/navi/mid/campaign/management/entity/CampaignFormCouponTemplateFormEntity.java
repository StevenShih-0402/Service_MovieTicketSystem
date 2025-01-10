package com.ctbcbank.navi.mid.campaign.management.entity;

import com.ibm.cbmp.fabric.foundation.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "TB_CAMPAIGN_FORM_COUPON_TEMPLATE_FORM")
public class CampaignFormCouponTemplateFormEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Comment("活動表單編號@@N")
    @Column(name = "CAMPAIGN_FORM_NO", nullable = false, updatable = false)
    private String campaignFormNo;

    @Comment("活動編號@@N")
    @Column(name = "CAMPAIGN_NO", nullable = false, updatable = false)
    private String campaignNo;

    @Comment("優惠券樣板表單編號@@N")
    @Column(name = "COUPON_TEMPLATE_FORM_NO", nullable = false, updatable = false)
    private String couponTemplateFormNo;

    @Comment("優惠券樣板編號@@N")
    @Column(name = "COUPON_TEMPLATE_NO", nullable = false, updatable = false)
    private String couponTemplateNo;

}
