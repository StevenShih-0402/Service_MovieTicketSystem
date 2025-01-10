package com.ctbcbank.navi.mid.campaign.management.service.campaign.createcoupontemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignCreateCouponTemplateRqBo {
    private String campaignNo;
    private String couponTemplateNo;
}
