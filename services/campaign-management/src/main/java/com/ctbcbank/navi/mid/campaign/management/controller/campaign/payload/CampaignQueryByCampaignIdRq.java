package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class CampaignQueryByCampaignIdRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "行銷活動ID", description = "行銷活動ID", example = "1")
    @NotNull
    @JsonProperty("campaignId")
    private BigInteger campaignId;
}
