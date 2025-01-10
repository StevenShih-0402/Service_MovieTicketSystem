package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignCreateRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "活動編號", description = "活動編號", example = "1")
    @JsonProperty("campaignId")
    private BigInteger campaignId;

}
