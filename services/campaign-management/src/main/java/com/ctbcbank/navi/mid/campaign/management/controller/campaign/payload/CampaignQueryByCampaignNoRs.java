package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignQueryByCampaignNoRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "行銷活動", description = "行銷活動")
    private CampaignInfo campaignInfo;

    @Schema(title = "行銷活動節點", description = "行銷活動節點")
    private List<CampaignGroupNode> groupNodes;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignInfo {
        @Schema(title = "活動唯一識別碼", description = "活動唯一識別碼", example = "1")
        private BigInteger id;

        @Schema(title = "活動編號", description = "活動編號", example = "a6f622d1-3410-4f61-8605-1e44c073fbd8")
        private String campaignNo;

        @Schema(title = "是否上架", description = "是否上架", example = "true")
        private Boolean isListing;

        @Schema(title = "活動類別", description = "活動類別", example = "")
        private String category;

        @Schema(title = "活動名稱", description = "活動名稱", example = "活動名稱")
        private String name;

        @Schema(title = "活動描述", description = "活動描述", example = "活動描述")
        private String description;

        @Schema(title = "即時活動", description = "即時活動", example = "true")
        private Boolean isImmediate;

        @Schema(title = "即時交易代碼", description = "即時交易代碼", example = "TRANSFER")
        private String immediateTransactionCode;

        @Schema(title = "開始時間", description = "開始時間")
        private LocalDateTime startDateTime;

        @Schema(title = "結束時間", description = "結束時間")
        private LocalDateTime endDateTime;

        @Schema(title = "員工編號-創建者", description = "員工編號-創建者", example = "A001")
        private String createEmployeeNo;

        @Schema(title = "客戶名單編號", description = "客戶名單編號", example = "8158a3ed-ec33-4007-babd-c59f9a828c60")
        private String customerListNo;

        @Schema(title = "參與類型", description = "參與類型", example = "FREE")
        private CampaignParticipantTypeEnum participantType;

        @Schema(title = "參與名單限制數量", description = "參與名單限制數量", example = "100")
        private BigInteger participantListLimit;

        @Schema(title = "建立時間", description = "建立時間")
        private LocalDateTime createDttm;

        @Schema(title = "更新時間", description = "更新時間")
        private LocalDateTime updateDttm;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignGroupNode {
        @Schema(title = "節點資料", description = "節點資料")
        private CampaignGroupNodeData groupNodeData;

        @Schema(title = "子節點", description = "子節點")
        private List<CampaignGroupNode> childrenGroupNodes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignGroupNodeData {
        @Schema(title = "規則設定清單", description = "規則設定清單")
        private List<CampaignRuleSetting> ruleSettings;

        @Schema(title = "優惠券樣板清單", description = "優惠券樣板清單")
        private List<CampaignCouponTemplateSetting> couponTemplateSettings;

        @Schema(title = "條件滿足數量", description = "條件滿足數量", example = "1")
        private Long matchCount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignRuleSetting {
        @Schema(title = "規則交易代碼", description = "規則交易代碼", example = "transfer")
        private String transactionCode;

        @Schema(title = "欄位名稱", description = "欄位名稱", example = "fromCurrency")
        private String fieldName;

        @Schema(title = "規則名稱", description = "規則名稱", example = "TRANSFER_COUNT_RULE")
        private String ruleName;

        @Schema(title = "規則設定值", description = "規則設定值", example = "1")
        private String ruleValue;

        @Schema(title = "規則型態", description = "規則型態", example = "EQUAL")
        private String ruleType;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignCouponTemplateSetting {

        @Schema(title = "優惠券Template編號", description = "優惠券Template編號", example = "1")
        @JsonProperty("couponTemplateNo")
        private String couponTemplateNo;

    }


}
