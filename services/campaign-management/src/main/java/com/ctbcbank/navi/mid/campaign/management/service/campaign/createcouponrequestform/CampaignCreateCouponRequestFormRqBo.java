package com.ctbcbank.navi.mid.campaign.management.service.campaign.createcouponrequestform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignCreateCouponRequestFormRqBo {
    private String campaignNo;
    private String couponTemplateNo;
    private String couponRequestFormNo;
}
