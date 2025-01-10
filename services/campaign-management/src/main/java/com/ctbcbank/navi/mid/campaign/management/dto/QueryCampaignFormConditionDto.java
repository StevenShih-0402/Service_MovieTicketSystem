package com.ctbcbank.navi.mid.campaign.management.dto;

import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCampaignFormConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String campaignFormNo;
    private List<ReviewStatusEnum> reviewStatusList;

}
