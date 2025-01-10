package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCampaignFormCouponTemplateFormConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String campaignFormNo;
    private String campaignNo;
    private String couponTemplateFormNo;
    private String couponTemplateNo;
}
