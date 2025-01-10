package com.ctbcbank.navi.mid.campaign.management.service.campaign.querycoupontemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignQueryCouponTemplateRsBo {

    private List<CampaignCouponTemplateBo> campaignCouponTemplateList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignCouponTemplateBo {
        private String campaignNo;

        private String couponTemplateNo;
    }

}
