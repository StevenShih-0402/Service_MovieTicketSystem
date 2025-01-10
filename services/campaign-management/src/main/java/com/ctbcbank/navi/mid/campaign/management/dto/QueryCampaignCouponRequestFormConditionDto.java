package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCampaignCouponRequestFormConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String campaignNo;
    private String couponTemplateNo;
    private String couponRequestFormNo;

}
