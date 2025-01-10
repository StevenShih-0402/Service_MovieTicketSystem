package com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory;

import com.ctbcbank.navi.mid.campaign.management.dto.BaseDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignFormTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignFormHistoryDto extends BaseDto {
    private String campaignFormNo;
    private String campaignNo;
    private String name;
    private String description;
    private String category;
    private Boolean isImmediate;
    private String immediateTransactionCode;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private ReviewStatusEnum reviewStatus;
    private CampaignFormTypeEnum campaignFormType;
    private Boolean isListing;
    private String groupNodeData;
    private String createEmployeeNo;
    private String updateEmployeeNo;
    private Boolean isParticipantList;
}
