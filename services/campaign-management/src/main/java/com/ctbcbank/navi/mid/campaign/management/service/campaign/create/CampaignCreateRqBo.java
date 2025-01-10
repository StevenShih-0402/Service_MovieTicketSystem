package com.ctbcbank.navi.mid.campaign.management.service.campaign.create;

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
public class CampaignCreateRqBo {

    private CampaignInfoBo campaignInfo;

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
    public class CampaignGroupNodeBo {

        private CampaignGroupNodeDataBo groupNodeData;

        private List<CampaignGroupNodeBo> childrenGroupNodes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CampaignGroupNodeDataBo {

        private List<CampaignRuleSettingBo> ruleSettings;

        private List<CampaignCouponTemplateSettingBo> couponTemplateSettings;

        private BigInteger matchCount;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CampaignRuleSettingBo {

        private BigInteger id;

        private Boolean isExtraRuleSetting;

        private String transactionCode;

        private String fieldName;

        private String ruleName;

        private String ruleValue;

        private String ruleType;

        private BigInteger transactionPayloadConfigId;

        private BigInteger transactionUrlConfigId;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CampaignCouponTemplateSettingBo {

        private String couponTemplateNo;
        private String couponTemplateFormNo;

    }
}
