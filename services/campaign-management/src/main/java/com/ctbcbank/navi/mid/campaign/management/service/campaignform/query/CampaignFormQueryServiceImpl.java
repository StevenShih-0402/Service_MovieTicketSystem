package com.ctbcbank.navi.mid.campaign.management.service.campaignform.query;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormCommentDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormCommentDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormCommentConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormDto;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormQueryServiceImpl implements CampaignFormQueryService {
    private final String CLASS_NAME = CampaignFormQueryServiceImpl.class.getSimpleName();
    private final CampaignFormDao campaignFormDao;
    private final CampaignFormCommentDao campaignFormCommentDao;

    @Override
    public CampaignFormQueryRsBo query(CampaignFormQueryRqBo campaignFormQueryRqBo) {
        QueryCampaignFormConditionDto campaignFormConditionDto = getCampaignFormConditionDto(campaignFormQueryRqBo);
        List<CampaignFormDto> campaignFormDtoList = campaignFormDao.queryCampaignForm(campaignFormConditionDto);
        log.info("[{}][query][campaignFormDtoList.size: {}]", CLASS_NAME, campaignFormDtoList.size());
        List<String> campaignFormNoList = campaignFormDtoList.stream().map(CampaignFormDto::getCampaignFormNo).toList();
        List<CampaignFormCommentDto> campaignFormCommentDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(campaignFormNoList)) {
            QueryCampaignFormCommentConditionDto queryCampaignFormCommentConditionDto = new QueryCampaignFormCommentConditionDto();
            queryCampaignFormCommentConditionDto.setCampaignFormNoList(campaignFormNoList);
            campaignFormCommentDtoList = campaignFormCommentDao.queryCampaignFormComment(queryCampaignFormCommentConditionDto);
            log.info("[{}][query][campaignFormCommentDtoList.size: {}]", CLASS_NAME, campaignFormCommentDtoList.size());
        }


        CampaignFormQueryRsBo campaignFormQueryRsBo = new CampaignFormQueryRsBo();
        campaignFormQueryRsBo.setCampaignFormInfoList(getCampaignFormInfoList(campaignFormDtoList, campaignFormCommentDtoList));
        return campaignFormQueryRsBo;
    }

    private QueryCampaignFormConditionDto getCampaignFormConditionDto(CampaignFormQueryRqBo campaignFormQueryRqBo) {
        QueryCampaignFormConditionDto campaignFormConditionDto = new QueryCampaignFormConditionDto();
        if (!CollectionUtils.isEmpty(campaignFormQueryRqBo.getReviewStatusList())) {
            campaignFormConditionDto.setReviewStatusList(campaignFormQueryRqBo.getReviewStatusList());
        }
        return campaignFormConditionDto;
    }

    private List<CampaignFormQueryRsBo.CampaignFormInfoBo> getCampaignFormInfoList(List<CampaignFormDto> campaignFormDtoList, List<CampaignFormCommentDto> campaignFormCommentDtoList) {
        List<CampaignFormQueryRsBo.CampaignFormInfoBo> campaignFormInfoList = campaignFormDtoList.stream().map(x -> {
            CampaignFormQueryRsBo.CampaignFormInfoBo campaignFormInfo = new CampaignFormQueryRsBo.CampaignFormInfoBo();
            campaignFormInfo.setCampaignFormNo(x.getCampaignFormNo());
            campaignFormInfo.setCampaignNo(x.getCampaignNo());
            campaignFormInfo.setName(x.getName());
            campaignFormInfo.setDescription(x.getDescription());
            campaignFormInfo.setCategory(x.getCategory());
            campaignFormInfo.setIsImmediate(x.getIsImmediate());
            campaignFormInfo.setImmediateTransactionCode(x.getImmediateTransactionCode());
            campaignFormInfo.setStartDateTime(x.getStartDateTime());
            campaignFormInfo.setEndDateTime(x.getEndDateTime());
            campaignFormInfo.setReviewStatus(x.getReviewStatus());
            campaignFormInfo.setCampaignFormType(x.getCampaignFormType());
            campaignFormInfo.setIsListing(x.getIsListing());
            campaignFormInfo.setGroupNodeData(x.getGroupNodeData());
            campaignFormInfo.setCreateEmployeeNo(x.getCreateEmployeeNo());
            campaignFormInfo.setCreateDttm(x.getCreateDttm());
            campaignFormInfo.setUpdateDttm(x.getUpdateDttm());
            return campaignFormInfo;
        }).toList();
        return campaignFormInfoList;
    }
}
