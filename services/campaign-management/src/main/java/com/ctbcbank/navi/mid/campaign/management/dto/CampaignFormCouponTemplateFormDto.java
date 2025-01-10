package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignFormCouponTemplateFormDto extends BaseDto {
    private String campaignFormNo;
    private String campaignNo;
    private String couponTemplateFormNo;
    private String couponTemplateNo;
}
