package com.ctbcbank.navi.mid.campaign.management.service.campaign.querycouponrequestform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignQueryCouponRequestFormRqBo {

    private String campaignNo;

    private String couponTemplateNo;

    private String couponRequestFormNo;
}
