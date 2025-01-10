package com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignParticipantListDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignparticipantlist.CampaignParticipantListDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignparticipantlist.QueryCampaignParticipantListConditionDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantListSourceTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import com.ibm.cbmp.fabric.foundation.context.NaviGlobalContext;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import com.ibm.cbmp.fabric.foundation.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignAddParticipantListServiceImpl implements CampaignAddParticipantListService {
    private final String CLASS_NAME = CampaignAddParticipantListServiceImpl.class.getSimpleName();
    private final CampaignDao campaignDao;
    private final CampaignParticipantListDao campaignParticipantListDao;
    private final String CAMPAIGN_INSERT_BATCH_SIZE = "campaign.insert-batch-size";

    @Override
    @Transactional
    public void addParticipantList(CampaignAddParticipantListRqBo campaignAddParticipantListRqBo) {
        String campaignNo = campaignAddParticipantListRqBo.getCampaignNo();
        BigInteger ipNo = campaignAddParticipantListRqBo.getIpNo();
        QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
        queryCampaignConditionDto.setCampaignNo(campaignNo);
        List<CampaignDto> campaignDtoList = campaignDao.queryCampaign(queryCampaignConditionDto);
        if (CollectionUtils.isEmpty(campaignDtoList) || campaignDtoList.size() > 1) {
            throw new NaviException(FabricResponseCode.INVALID_DATA, "CampaignNo: " + campaignNo + ", Data Not Found || size > 1");
        }
        CampaignDto campaignDto = campaignDtoList.get(0);

        CampaignParticipantTypeEnum participantType = campaignDto.getParticipantType();
        if (participantType.compareTo(CampaignParticipantTypeEnum.ONLINE) != 0) {
            throw new NaviException(FabricResponseCode.INVALID_DATA, "Not ONLINE Campaign.");
        }
        QueryCampaignParticipantListConditionDto queryCampaignParticipantListConditionDto = new QueryCampaignParticipantListConditionDto();
        queryCampaignParticipantListConditionDto.setCampaignNo(campaignNo);
        List<CampaignParticipantListDto> campaignParticipantListDtoList = campaignParticipantListDao.queryCampaignParticipantList(queryCampaignParticipantListConditionDto);
        List<CampaignParticipantListDto> filterCampaignParticipantListDtoList = campaignParticipantListDtoList.stream().filter(x -> x.getIpNo().compareTo(ipNo) == 0).toList();
        if (!CollectionUtils.isEmpty(filterCampaignParticipantListDtoList)) {
            return;
        }
        BigInteger participantListLimit = campaignDto.getParticipantListLimit();
        BigInteger participantListCount = BigInteger.valueOf(campaignParticipantListDtoList.size() + 1);
        if (participantListCount.compareTo(participantListLimit) > 0) {
            throw new NaviException(FabricResponseCode.INVALID_DATA, "Over ParticipantListLimit.(" + participantListLimit + "," + participantListCount + ")");
        }
        String participantListVersion = campaignDto.getParticipantListVersion();

        CampaignParticipantListDto campaignParticipantListDto = new CampaignParticipantListDto();
        campaignParticipantListDto.setCampaignNo(campaignNo);
        campaignParticipantListDto.setIpNo(ipNo);
        campaignParticipantListDto.setSourceType(CampaignParticipantListSourceTypeEnum.ONLINE_CLICK);
        campaignParticipantListDto.setParticipantListVersion(participantListVersion);
        campaignParticipantListDao.saveCampaignParticipantList(campaignParticipantListDto);

    }

}
