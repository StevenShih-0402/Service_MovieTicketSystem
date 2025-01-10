package com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.query;

import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormQueryRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "審核狀態", description = "審核狀態", example = "[\"DRAFT\"]")
    private List<ReviewStatusEnum> reviewStatusList;

}
