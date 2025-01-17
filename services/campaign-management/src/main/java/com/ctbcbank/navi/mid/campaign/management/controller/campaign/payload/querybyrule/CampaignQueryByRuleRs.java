package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.querybyrule;

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
public class CampaignQueryByRuleRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "總筆數", description = "總筆數", example = "1")
    private Long totalElements;

    @Schema(title = "總頁數", description = "總頁數", example = "5")
    private Integer totalPages;

    @Schema(title = "當前頁數", description = "當前頁數", example = "1")
    private Integer number;

    @Schema(title = "每頁大小", description = "每頁大小", example = "1")
    private Integer size;

    @Schema(title = "活動清單", description = "活動清單")
    private List<Campaign> campaignList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Campaign {
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

        @Schema(title = "建立時間", description = "建立時間")
        private LocalDateTime createDttm;

        @Schema(title = "更新時間", description = "更新時間")
        private LocalDateTime updateDttm;
    }

}
