package com.ctbcbank.navi.mid.campaign.management.service.campaignform.updatereviewstatus;

import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormUpdateReviewStatusRqBo {
    private String campaignFormNo;
    private ReviewStatusEnum reviewStatus;
    private String updateEmployeeNo;
}
