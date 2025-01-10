package com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignQueryRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "類別", description = "類別", example = "[\"DESP_AND_EX\"]")
    private List<String> categoryList;

    @Schema(title = "是否上架", description = "是否上架", example = "[true]")
    private List<Boolean> listingList;

    @Schema(title = "是否即時", description = "是否即時", example = "true")
    private Boolean isImmediate;

    @Schema(title = "結束區間的開始時間", description = "查詢結束時間的開始時間")
    private LocalDateTime endStartDateTime;

    @Schema(title = "結束區間的結束時間", description = "查詢結束時間的結束時間")
    private LocalDateTime endEndDatetime;

    @Schema(title = "參與類別清單", description = "參與類別清單", example = "[\"FREE\"]")
    private List<CampaignParticipantTypeEnum> participantTypeList;

}
