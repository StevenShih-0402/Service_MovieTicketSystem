package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.addparticipantlist;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignAddParticipantListStatusEnum;
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
public class CampaignAddParticipantListRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "狀態", description = "狀態", example = "COMPLETED")
    private CampaignAddParticipantListStatusEnum status;

    @Schema(title = "訊息", description = "訊息", example = "完成參與")
    private String message;
}
