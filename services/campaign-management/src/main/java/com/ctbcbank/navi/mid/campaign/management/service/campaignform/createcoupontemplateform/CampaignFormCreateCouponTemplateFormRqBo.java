package com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcoupontemplateform;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormCreateCouponTemplateFormRqBo {
    private String campaignFormNo;
    private String campaignNo;
    private String couponTemplateFormNo;
    private String couponTemplateNo;
}
