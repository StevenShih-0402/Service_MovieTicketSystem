package com.ctbcbank.navi.mid.campaign.management.service.campaign.querycoupontemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignQueryCouponTemplateRqBo {
    private String campaignNo;
    private String couponTemplateNo;
}
