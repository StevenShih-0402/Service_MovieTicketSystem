package com.ctbcbank.navi.mid.campaign.management.service.campaignform.querycoupontemplateform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormQueryCouponTemplateFormRsBo {

    private List<CampaignFormCouponTemplateFormBo> campaignFormCouponTemplateFormList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignFormCouponTemplateFormBo {
        private String campaignFormNo;
        private String couponTemplateFormNo;
    }
}
