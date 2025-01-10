package com.ctbcbank.navi.mid.campaign.management.service.campaignform.update;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormUpdateRqBo {
    private String campaignFormNo;
    private Boolean isUpdateCampaignFormInfo;
    private Boolean isUpdateCampaignFormParticipantList;
    private Boolean isUpdateCampaignFormGroupNode;
    private CampaignFormInfoBo campaignFormInfo;
    private Boolean isParticipantList;
    private String customerListNo;
    private CampaignParticipantTypeEnum participantType;
    private BigInteger participantListLimit;
    private String groupNodeData;
    private String updateEmployeeNo;


    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignFormInfoBo {
        private String name;

        private String description;

        private String category;

        private Boolean isImmediate;

        private String immediateTransactionCode;

        private LocalDateTime startDateTime;

        private LocalDateTime endDateTime;

        private Boolean isListing;

    }

}
