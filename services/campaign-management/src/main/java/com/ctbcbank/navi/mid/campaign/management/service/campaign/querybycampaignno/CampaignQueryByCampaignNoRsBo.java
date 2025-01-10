package com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignno;

import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryByCampaignNoRs;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class CampaignQueryByCampaignNoRsBo {

    private CampaignInfoBo campaignInfoBo;
    private List<CampaignGroupNodeBo> groupNodeBos;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignInfoBo {
        private BigInteger id;

        private String campaignNo;

        private Boolean isListing;

        private String category;

        private String name;

        private String description;

        private Boolean isImmediate;

        private String immediateTransactionCode;

        private LocalDateTime startDateTime;

        private LocalDateTime endDateTime;

        private String createEmployeeNo;

        private Boolean isParticipantList;

        private String customerListNo;

        private CampaignParticipantTypeEnum participantType;
        
        private BigInteger participantListLimit;

        private LocalDateTime createDttm;

        private LocalDateTime updateDttm;
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

        private Long matchCount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignRuleSettingBo {
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

    }
}
