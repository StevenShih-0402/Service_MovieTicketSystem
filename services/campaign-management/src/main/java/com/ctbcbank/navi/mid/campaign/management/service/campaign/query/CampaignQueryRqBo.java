package com.ctbcbank.navi.mid.campaign.management.service.campaign.query;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignQueryRqBo {
    private List<String> categoryList;
    private List<Boolean> listingList;
    private Boolean isImmediate;
    private LocalDateTime endStartDateTime;
    private LocalDateTime endEndDatetime;
    private List<CampaignParticipantTypeEnum> participantTypeList;
}
