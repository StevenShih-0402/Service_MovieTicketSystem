package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCampaignFormCommentConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> campaignFormNoList;
    
}
