package com.ctbcbank.navi.mid.campaign.management.dto;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCampaignConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigInteger id;

    private String campaignNo;

    private LocalDateTime campaignDateTime;

    private List<String> categoryList;

    private LocalDateTime endStartDateTime;

    private LocalDateTime endEndDateTime;

    private Boolean isImmediate;

    private Boolean isListing;
    private List<Boolean> listingList;
    private List<CampaignParticipantTypeEnum> participantTypeList;

}
