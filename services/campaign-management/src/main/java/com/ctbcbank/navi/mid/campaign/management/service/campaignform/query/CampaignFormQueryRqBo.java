package com.ctbcbank.navi.mid.campaign.management.service.campaignform.query;

import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignFormQueryRqBo {

    private List<ReviewStatusEnum> reviewStatusList;

}
