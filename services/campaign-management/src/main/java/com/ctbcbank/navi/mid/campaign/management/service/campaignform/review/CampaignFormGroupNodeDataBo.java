package com.ctbcbank.navi.mid.campaign.management.service.campaignform.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormGroupNodeDataBo {

    private List<GroupNodeBo> groupNodeList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupNodeBo {
        private GroupNodeDataBo data;
        private List<GroupNodeBo> children;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupNodeDataBo {
        private List<RuleSettingBo> ruleSettings;
        private List<CouponTemplateSettingBo> couponTemplateSettings;
        private BigInteger matchCount;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RuleSettingBo {
        private String transactionCode;
        private String fieldName;
        private String ruleName;
        private String ruleValue;
        private String ruleType;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CouponTemplateSettingBo {
        private String couponTemplateNo;
        private String couponTemplateFormNo;
    }
}
