package com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.create;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignFormTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormCreateRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "活動編號", description = "活動編號", example = "a6f622d1-3410-4f61-8605-1e44c073fbd8")
    private String campaignNo;

    @NotBlank
    @Schema(title = "活動名稱", description = "活動名稱", example = "活動名稱")
    private String name;

    @Schema(title = "活動描述", description = "活動描述", example = "活動描述")
    private String description;

    @NotBlank
    @Schema(title = "活動類別", description = "活動類別", example = "")
    private String category;

    @NotNull
    @Schema(title = "即時活動", description = "即時活動", example = "true")
    private Boolean isImmediate;

    @Schema(title = "即時活動交易代碼", description = "即時活動交易代碼", example = "EXCHANGE")
    private String immediateTransactionCode;

    @NotNull
    @Schema(title = "開始時間", description = "開始時間", example = "2024-01-01T00:00:00")
    private LocalDateTime startDateTime;

    @NotNull
    @Schema(title = "結束時間", description = "結束時間", example = "2024-01-01T00:00:00")
    private LocalDateTime endDateTime;

    @NotNull
    @Schema(title = "表單類型", description = "表單類型", example = "CREATE")
    private CampaignFormTypeEnum campaignFormType;

    @NotNull
    @Schema(title = "是否上架", description = "是否上架", example = "true")
    private Boolean isListing;

    @Schema(title = "節點資訊", description = "節點資訊", example = "{}")
    private String groupNodeData;

    @NotBlank
    @Schema(title = "員工編號-創建者", description = "員工編號-創建者", example = "A001")
    private String createEmployeeNo;

    @Schema(title = "客戶名單編號", description = "客戶名單編號", example = "8158a3ed-ec33-4007-babd-c59f9a828c60")
    private String customerListNo;

    @Schema(title = "參與類型", description = "參與類型", example = "FREE")
    private CampaignParticipantTypeEnum participantType;

    @Schema(title = "參與名單限制數量", description = "參與名單限制數量", example = "100")
    private BigInteger participantListLimit;

}
