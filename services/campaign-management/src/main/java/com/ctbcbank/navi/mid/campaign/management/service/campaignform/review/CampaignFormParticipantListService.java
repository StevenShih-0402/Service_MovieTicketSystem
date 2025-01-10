package com.ctbcbank.navi.mid.campaign.management.service.campaignform.review;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignDto;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormDto;

public interface CampaignFormParticipantListService {
    void asyncProcessParticipantList(CampaignFormReviewRqBo campaignFormReviewRqBo);
}
