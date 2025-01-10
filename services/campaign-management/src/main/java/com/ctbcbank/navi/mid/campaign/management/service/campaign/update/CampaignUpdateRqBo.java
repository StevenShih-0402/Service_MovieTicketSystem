package com.ctbcbank.navi.mid.campaign.management.service.campaign.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignUpdateRqBo {
    private boolean isUpdateCampaignInfo;
    private boolean isUpdateCampaignGroupNode;

    private CampaignInfoBo campaignInfoBo;
    private List<CampaignGroupNodeBo> groupNodes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignInfoBo {
        private BigInteger id;

        private String name;

        private String description;

        private String category;

        private String status;

        private String isImmediate;

        private String immediateTransactionCode;

        private LocalDateTime startDateTime;

        private LocalDateTime endDateTime;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignGroupNodeBo {

        private CampaignGroupNodeDataBo groupNodeData;

        private List<CampaignGroupNodeBo> childrenGroupNodes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignGroupNodeDataBo {

        private List<CampaignRuleSettingBo> ruleSettings;

        private List<CampaignCouponTemplateSettingBo> couponTemplateSettings;

        private BigInteger matchCount;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignRuleSettingBo {

        private BigInteger id;

        private Boolean isExtraRuleSetting;

        private String transactionCode;

        private String fieldName;

        private String ruleName;

        private String ruleValue;

        private String ruleType;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignCouponTemplateSettingBo {

        private String couponTemplateNo;

        private String couponTemplateFormNo;

    }
}
