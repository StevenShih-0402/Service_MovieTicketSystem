package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignCreateCouponTemplateRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "活動編號", description = "活動編號", example = "a6f622d1-3410-4f61-8605-1e44c073fbd8")
    private String campaignNo;

    @Schema(title = "優惠券樣板編號", description = "優惠券樣板編號", example = "a6f622d1-3410-4f61-8605-1e44c073fbd8")
    private String couponTemplateNo;

}
