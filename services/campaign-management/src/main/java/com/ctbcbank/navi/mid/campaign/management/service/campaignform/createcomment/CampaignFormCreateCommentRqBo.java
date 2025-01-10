package com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcomment;

import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormCreateCommentRqBo {
    private String campaignFormNo;
    private String campaignNo;
    private ReviewStatusEnum campaignFormCommentType;
    private String campaignFormComment;
    private String createEmployeeNo;
}
