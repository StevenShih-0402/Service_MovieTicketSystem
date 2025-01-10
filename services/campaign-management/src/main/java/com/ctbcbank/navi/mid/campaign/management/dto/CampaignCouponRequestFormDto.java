package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignCouponRequestFormDto extends BaseDto {
    private String campaignNo;
    private String couponTemplateNo;
    private String couponRequestFormNo;
}
