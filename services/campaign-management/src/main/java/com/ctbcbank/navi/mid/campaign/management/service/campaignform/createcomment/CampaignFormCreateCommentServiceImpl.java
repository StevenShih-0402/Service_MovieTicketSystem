package com.ctbcbank.navi.mid.campaign.management.service.campaignform.createcomment;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormCommentDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormCommentDto;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.updatereviewstatus.CampaignFormUpdateReviewStatusServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormCreateCommentServiceImpl implements CampaignFormCreateCommentService {
    private final String CLASS_NAME = CampaignFormCreateCommentServiceImpl.class.getSimpleName();
    private final CampaignFormCommentDao campaignFormCommentDao;

    @Override
    public CampaignFormCreateCommentRsBo createComment(CampaignFormCreateCommentRqBo campaignFormCreateCommentRqBo) {
        CampaignFormCommentDto campaignFormCommentDto = getCampaignFormCommentDto(campaignFormCreateCommentRqBo);
        CampaignFormCommentDto saveCampaignFormCommentDto = campaignFormCommentDao.saveCampaignFormComment(campaignFormCommentDto);
        CampaignFormCreateCommentRsBo campaignFormCreateCommentRsBo = getCampaignFormCreateCommentRsBo(saveCampaignFormCommentDto);
        return campaignFormCreateCommentRsBo;
    }

    private CampaignFormCommentDto getCampaignFormCommentDto(CampaignFormCreateCommentRqBo campaignFormCreateCommentRqBo) {
        CampaignFormCommentDto campaignFormCommentDto = new CampaignFormCommentDto();
        campaignFormCommentDto.setCampaignFormNo(campaignFormCreateCommentRqBo.getCampaignFormNo());
        campaignFormCommentDto.setCampaignNo(campaignFormCreateCommentRqBo.getCampaignNo());
        campaignFormCommentDto.setCampaignFormCommentType(campaignFormCreateCommentRqBo.getCampaignFormCommentType());
        campaignFormCommentDto.setComment(campaignFormCreateCommentRqBo.getCampaignFormComment());
        campaignFormCommentDto.setCreateEmployeeNo(campaignFormCreateCommentRqBo.getCreateEmployeeNo());
        return campaignFormCommentDto;
    }

    private CampaignFormCreateCommentRsBo getCampaignFormCreateCommentRsBo(CampaignFormCommentDto campaignFormCommentDto) {
        CampaignFormCreateCommentRsBo campaignFormCreateCommentRsBo = new CampaignFormCreateCommentRsBo();
        campaignFormCreateCommentRsBo.setCampaignFormNo(campaignFormCommentDto.getCampaignFormNo());
        campaignFormCreateCommentRsBo.setCampaignNo(campaignFormCommentDto.getCampaignNo());
        campaignFormCreateCommentRsBo.setCampaignFormCommentType(campaignFormCommentDto.getCampaignFormCommentType());
        campaignFormCreateCommentRsBo.setCampaignFormComment(campaignFormCommentDto.getComment());
        return campaignFormCreateCommentRsBo;
    }

}
