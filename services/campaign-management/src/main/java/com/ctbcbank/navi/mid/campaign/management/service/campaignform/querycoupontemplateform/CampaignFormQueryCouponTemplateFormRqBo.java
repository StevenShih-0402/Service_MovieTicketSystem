package com.ctbcbank.navi.mid.campaign.management.service.campaignform.querycoupontemplateform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormQueryCouponTemplateFormRqBo {
    private String campaignFormNo;
    private String couponTemplateFormNo;
}
