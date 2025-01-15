package com.ctbcbank.navi.mid.campaign.management.dto;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDto extends BaseDto {
    private String campaignNo;
    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String category;
    private Boolean isImmediate;
    private String immediateTransactionCode;
    private Boolean isListing;
    private String createEmployeeNo;
    private String customerListNo;
    private String participantListVersion;
    private CampaignParticipantTypeEnum participantType;
    private BigInteger participantListLimit;
}
