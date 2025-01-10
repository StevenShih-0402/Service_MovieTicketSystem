package com.ctbcbank.navi.mid.campaign.management.service.campaignform.querybycampaignformno;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignFormTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
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
public class CampaignFormQueryByFormNoRsBo {
    private CampaignFormInfoBo campaignFormInfo;
    private List<CampaignFormCommentBo> campaignFormCommentList;
    private List<CampaignFormParticipantInfoBo> campaignFormParticipantInfoList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignFormInfoBo {
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

        private CampaignParticipantTypeEnum participantType;

        private BigInteger participantListLimit;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignFormCommentBo {

        private ReviewStatusEnum campaignFormCommentType;

        private String comment;

        private String createEmployeeNo;

        private LocalDateTime createDttm;

        private LocalDateTime updateDttm;

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignFormParticipantInfoBo {
        private String cifNo;

        private String idNo;

        private BigInteger ipNo;
    }

}
