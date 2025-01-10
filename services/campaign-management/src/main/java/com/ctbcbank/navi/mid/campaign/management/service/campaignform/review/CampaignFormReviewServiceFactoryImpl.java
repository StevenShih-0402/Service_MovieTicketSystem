package com.ctbcbank.navi.mid.campaign.management.service.campaignform.review;

import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.impl.CampaignFormReviewApprovedServiceImpl;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.impl.CampaignFormReviewRejectedServiceImpl;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormReviewServiceFactoryImpl implements CampaignFormReviewServiceFactory {
    private final CampaignFormReviewApprovedServiceImpl campaignFormReviewApprovedServiceImpl;
    private final CampaignFormReviewRejectedServiceImpl campaignFormReviewRejectedServiceImpl;

    public CampaignFormReviewService getCampaignFormReviewService(CampaignFormReviewRqBo campaignFormReviewRqBo) {
        ReviewStatusEnum reviewStatusEnum = campaignFormReviewRqBo.getReviewStatus();
        switch (reviewStatusEnum) {
            case APPROVED -> {
                return campaignFormReviewApprovedServiceImpl;
            }
            case REJECTED -> {
                return campaignFormReviewRejectedServiceImpl;
            }
            default -> throw new NaviException(FabricResponseCode.INVALID_DATA, "invalid reviewStatus = " + reviewStatusEnum);
        }
    }
}
