package com.ctbcbank.navi.mid.campaign.management.service.campaignform.create;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignFormTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormCreateRsBo {
    private String campaignFormNo;
    private String campaignNo;
    private String name;
    private String description;
    private String category;
    private Boolean isImmediate;
    private String immediateTransactionCode;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private CampaignFormTypeEnum campaignFormType;
    private Boolean isListing;
    private String groupNodeData;
    private String createEmployeeNo;
    private String customerListNo;
}
