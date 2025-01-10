package com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.updatereviewstatus;

import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormUpdateReviewStatusRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(title = "活動表單編號", description = "活動表單編號", example = "a6f622d1-3410-4f61-8605-1e44c073fbd8")
    private String campaignFormNo;

    @NotNull
    @Schema(title = "活動表單狀態", description = "活動表單狀態", example = "DRAFT")
    private ReviewStatusEnum reviewStatus;

    @NotBlank
    @Schema(title = "員工編號-更新者", description = "員工編號-更新者", example = "A001")
    private String updateEmployeeNo;

}
