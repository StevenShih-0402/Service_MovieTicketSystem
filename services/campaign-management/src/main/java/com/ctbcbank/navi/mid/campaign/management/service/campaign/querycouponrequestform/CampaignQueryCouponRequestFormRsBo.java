package com.ctbcbank.navi.mid.campaign.management.service.campaign.querycouponrequestform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignQueryCouponRequestFormRsBo {

    private List<CampaignCouponRequestFormBo> campaignCouponRequestFormBoList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignCouponRequestFormBo {
        private String campaignNo;

        private String couponTemplateNo;

        private String couponRequestFormNo;
    }
}
