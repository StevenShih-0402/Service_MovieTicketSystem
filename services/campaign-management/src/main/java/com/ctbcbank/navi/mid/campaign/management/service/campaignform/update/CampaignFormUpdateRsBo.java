package com.ctbcbank.navi.mid.campaign.management.service.campaignform.update;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignFormTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormUpdateRsBo {
    private CampaignFormInfoBo campaignFormInfo;
    private String groupNodeData;

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

        private CampaignFormTypeEnum campaignFormType;

        private Boolean isListing;

    }

}
