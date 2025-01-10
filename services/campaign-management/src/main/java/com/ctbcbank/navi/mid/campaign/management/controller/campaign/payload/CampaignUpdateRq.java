package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
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
public class CampaignUpdateRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "是否更新行銷活動資訊", description = "是否更新行銷活動資訊", example = "true")
    @JsonProperty("isUpdateCampaignInfo")
    private boolean isUpdateCampaignInfo;

    @Schema(title = "是否更新行銷活動節點", description = "是否更新行銷活動節點", example = "true")
    @JsonProperty("isUpdateCampaignGroupNode")
    private boolean isUpdateCampaignGroupNode;

    @Schema(title = "行銷活動資訊", description = "行銷活動資訊")
    @JsonProperty("campaignInfo")
    private CampaignInfo campaignInfo;

    @Schema(title = "行銷活動節點", description = "行銷活動節點")
    @JsonProperty("groupNodes")
    private List<CampaignGroupNode> groupNodes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignInfo {
        @Schema(title = "活動編號", description = "活動編號", example = "1")
        @JsonProperty("id")
        private BigInteger id;

        @Schema(title = "活動名稱", description = "活動名稱", example = "活動名稱")
        @JsonProperty("name")
        private String name;

        @Schema(title = "活動描述", description = "活動描述", example = "活動描述")
        @JsonProperty("description")
        private String description;

        @Schema(title = "活動類別", description = "活動類別", example = "")
        @JsonProperty("category")
        private String category;

        @Schema(title = "狀態", description = "狀態", example = "LISTING")
        @JsonProperty("status")
        private String status;

        @Schema(title = "即時活動", description = "即時活動", example = "Y")
        @JsonProperty("isImmediate")
        private String isImmediate;

        @Schema(title = "即時活動交易代碼", description = "即時活動交易代碼", example = "EXCHANGE")
        @JsonProperty("immediateTransactionCode")
        private String immediateTransactionCode;

        @Schema(title = "開始時間", description = "開始時間")
        @JsonProperty("startDateTime")
        private LocalDateTime startDateTime;

        @Schema(title = "結束時間", description = "結束時間")
        @JsonProperty("endDateTime")
        private LocalDateTime endDateTime;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignGroupNode {

        @Schema(title = "節點資料", description = "節點資料")
        @JsonProperty("groupNodeData")
        private CampaignGroupNodeData groupNodeData;

        @Schema(title = "子節點", description = "子節點")
        @JsonProperty("children")
        private List<CampaignGroupNode> childrenGroupNodes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignGroupNodeData {

        @Schema(title = "規則設定清單", description = "規則設定清單")
        @JsonProperty("ruleSettings")
        private List<CampaignRuleSetting> ruleSettings;

        @Schema(title = "優惠券樣板清單", description = "優惠券樣板清單")
        @JsonProperty("couponTemplateSettings")
        private List<CampaignCouponTemplateSetting> couponTemplateSettings;

        @Schema(title = "條件滿足數量", description = "條件滿足數量", example = "1")
        @JsonProperty("matchCount")
        private BigInteger matchCount;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignRuleSetting {

        @Schema(title = "是否為額外規則設定", description = "是否為額外規則設定", example = "true")
        @JsonProperty("isExtraRuleSetting")
        private Boolean isExtraRuleSetting;

        @Schema(title = "規則交易代碼", description = "規則交易代碼", example = "transfer")
        @JsonProperty("transactionCode")
        private String transactionCode;

        @Schema(title = "欄位名稱", description = "欄位名稱", example = "toCurrency")
        @JsonProperty("fieldName")
        private String fieldName;

        @Schema(title = "規則名稱", description = "規則名稱", example = "TRANSFER_COUNT_RULE")
        @JsonProperty("ruleName")
        private String ruleName;

        @Schema(title = "規則設定值", description = "規則設定值", example = "1")
        @JsonProperty("ruleValue")
        private String ruleValue;

        @Schema(title = "規則型態", description = "規則型態", example = "EQUAL")
        @JsonProperty("ruleType")
        private String ruleType;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignCouponTemplateSetting {

        @Schema(title = "優惠券Template編號", description = "優惠券Template編號", example = "1")
        @JsonProperty("couponTemplateNo")
        private String couponTemplateNo;

        @Schema(title = "優惠券Template表單編號", description = "優惠券Template表單編號", example = "1")
        @JsonProperty("couponTemplateFormNo")
        private String couponTemplateFormNo;

    }
}
