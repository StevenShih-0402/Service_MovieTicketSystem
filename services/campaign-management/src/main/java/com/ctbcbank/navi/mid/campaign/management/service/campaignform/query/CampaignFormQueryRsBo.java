package com.ctbcbank.navi.mid.campaign.management.service.campaignform.query;

import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.query.CampaignFormQueryRs;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignFormTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class CampaignFormQueryRsBo {

    private List<CampaignFormInfoBo> campaignFormInfoList;

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

        private LocalDateTime createDttm;

        private LocalDateTime updateDttm;
    }
}
