package com.ctbcbank.navi.mid.campaign.management.dto;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignFormTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignFormDto extends BaseDto {
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
    private Boolean isParticipantList;
    private String customerListNo;
    private String participantListVersion;
    private CampaignParticipantTypeEnum participantType;
    private BigInteger participantListLimit;
}
