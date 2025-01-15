package com.ctbcbank.navi.mid.campaign.management.service.campaignform.querybycampaignformno;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormCommentDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormCommentDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormCommentConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormDto;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormQueryByFormNoServiceImpl implements CampaignFormQueryByFormNoService {
    private final String CLASS_NAME = CampaignFormQueryByFormNoServiceImpl.class.getSimpleName();
    private final CampaignFormDao campaignFormDao;
    private final CampaignFormCommentDao campaignFormCommentDao;


    @Override
    public CampaignFormQueryByFormNoRsBo queryByCampaignFormNo(CampaignFormQueryByFormNoRqBo campaignQueryByFormNoRqBo) {
        String campaignFormNo = campaignQueryByFormNoRqBo.getCampaignFormNo();
        QueryCampaignFormConditionDto campaignFormConditionDto = new QueryCampaignFormConditionDto();
        campaignFormConditionDto.setCampaignFormNo(campaignFormNo);
        List<CampaignFormDto> campaignFormDtoList = campaignFormDao.queryCampaignForm(campaignFormConditionDto);
        if (CollectionUtils.isEmpty(campaignFormDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "CampaignFormNo: " + campaignFormNo);
        }
        if (campaignFormDtoList.size() > 1) {
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "CampaignFormNo: " + campaignFormNo);
        }
        CampaignFormDto campaignFormDto = campaignFormDtoList.get(0);

        // 備註清單
        QueryCampaignFormCommentConditionDto queryCampaignFormCommentConditionDto = new QueryCampaignFormCommentConditionDto();
        queryCampaignFormCommentConditionDto.setCampaignFormNoList(Collections.singletonList(campaignFormNo));
        List<CampaignFormCommentDto> campaignFormCommentDtoList = campaignFormCommentDao.queryCampaignFormComment(queryCampaignFormCommentConditionDto);

        CampaignFormQueryByFormNoRsBo campaignFormQueryByFormNoRsBo = new CampaignFormQueryByFormNoRsBo();
        campaignFormQueryByFormNoRsBo.setCampaignFormInfo(getCampaignFormInfo(campaignFormDto));
        campaignFormQueryByFormNoRsBo.setCampaignFormCommentList(getCampaignFormCommentBoList(campaignFormCommentDtoList));
        return campaignFormQueryByFormNoRsBo;
    }

    private CampaignFormQueryByFormNoRsBo.CampaignFormInfoBo getCampaignFormInfo(CampaignFormDto campaignFormDto) {
        CampaignFormQueryByFormNoRsBo.CampaignFormInfoBo campaignFormInfo = new CampaignFormQueryByFormNoRsBo.CampaignFormInfoBo();
        campaignFormInfo.setCampaignFormNo(campaignFormDto.getCampaignFormNo());
        campaignFormInfo.setCampaignNo(campaignFormDto.getCampaignNo());
        campaignFormInfo.setName(campaignFormDto.getName());
        campaignFormInfo.setDescription(campaignFormDto.getDescription());
        campaignFormInfo.setCategory(campaignFormDto.getCategory());
        campaignFormInfo.setIsImmediate(campaignFormDto.getIsImmediate());
        campaignFormInfo.setImmediateTransactionCode(campaignFormDto.getImmediateTransactionCode());
        campaignFormInfo.setStartDateTime(campaignFormDto.getStartDateTime());
        campaignFormInfo.setEndDateTime(campaignFormDto.getEndDateTime());
        campaignFormInfo.setReviewStatus(campaignFormDto.getReviewStatus());
        campaignFormInfo.setCampaignFormType(campaignFormDto.getCampaignFormType());
        campaignFormInfo.setIsListing(campaignFormDto.getIsListing());
        campaignFormInfo.setGroupNodeData(campaignFormDto.getGroupNodeData());
        campaignFormInfo.setCreateEmployeeNo(campaignFormDto.getCreateEmployeeNo());
        campaignFormInfo.setCustomerListNo(campaignFormDto.getCustomerListNo());
        campaignFormInfo.setParticipantType(campaignFormDto.getParticipantType());
        campaignFormInfo.setParticipantListLimit(campaignFormDto.getParticipantListLimit());
        return campaignFormInfo;
    }

    private List<CampaignFormQueryByFormNoRsBo.CampaignFormCommentBo> getCampaignFormCommentBoList(List<CampaignFormCommentDto> campaignFormCommentDtoList) {
        List<CampaignFormQueryByFormNoRsBo.CampaignFormCommentBo> campaignFormCommentBoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignFormCommentDtoList)) {
            return campaignFormCommentBoList;
        }
        campaignFormCommentBoList = campaignFormCommentDtoList.stream().sorted((a, b) -> b.getCreateDttm().compareTo(a.getCreateDttm())).map(x -> {
            CampaignFormQueryByFormNoRsBo.CampaignFormCommentBo campaignFormCommentBo = new CampaignFormQueryByFormNoRsBo.CampaignFormCommentBo();
            campaignFormCommentBo.setCampaignFormCommentType(x.getCampaignFormCommentType());
            campaignFormCommentBo.setComment(x.getComment());
            campaignFormCommentBo.setCreateEmployeeNo(x.getCreateEmployeeNo());
            campaignFormCommentBo.setCreateDttm(x.getCreateDttm());
            campaignFormCommentBo.setUpdateDttm(x.getUpdateDttm());
            return campaignFormCommentBo;
        }).toList();
        return campaignFormCommentBoList;
    }

}
