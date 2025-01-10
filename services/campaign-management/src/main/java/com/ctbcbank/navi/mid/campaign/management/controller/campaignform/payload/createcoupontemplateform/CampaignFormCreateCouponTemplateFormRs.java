package com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.createcoupontemplateform;

import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormCreateCouponTemplateFormRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "活動表單編號", description = "活動表單編號", example = "a6f622d1-3410-4f61-8605-1e44c073fbd8")
    private String campaignFormNo;

    @Schema(title = "活動編號", description = "活動編號", example = "a6f622d1-3410-4f61-8605-1e44c073fbd8")
    private String campaignNo;

    @Schema(title = "優惠券樣板表單編號", description = "優惠券樣板表單編號", example = "a6f622d1-3410-4f61-8605-1e44c073fbd8")
    private String couponTemplateFormNo;

    @Schema(title = "優惠券樣板編號", description = "優惠券樣板編號", example = "a6f622d1-3410-4f61-8605-1e44c073fbd8")
    private String couponTemplateNo;

}
