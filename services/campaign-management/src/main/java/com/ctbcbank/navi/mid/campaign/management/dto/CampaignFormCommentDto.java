package com.ctbcbank.navi.mid.campaign.management.dto;

import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignFormCommentDto extends BaseDto {
    private String campaignFormNo;
    private String campaignNo;
    private ReviewStatusEnum campaignFormCommentType;
    private String comment;
    private String createEmployeeNo;
}
