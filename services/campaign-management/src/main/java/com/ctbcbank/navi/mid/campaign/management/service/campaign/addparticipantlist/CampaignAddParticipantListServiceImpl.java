package com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignParticipantListDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignparticipantlist.CampaignParticipantListDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignparticipantlist.QueryCampaignParticipantListConditionDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignAddParticipantListStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignAddParticipantListServiceImpl implements CampaignAddParticipantListService {
    private final String CLASS_NAME = CampaignAddParticipantListServiceImpl.class.getSimpleName();
    private final CampaignDao campaignDao;
    private final CampaignParticipantListDao campaignParticipantListDao;

    @Override
    @Transactional
    public CampaignAddParticipantListRsBo addParticipantList(CampaignAddParticipantListRqBo campaignAddParticipantListRqBo) {
        CampaignAddParticipantListRsBo campaignAddParticipantListRsBo = new CampaignAddParticipantListRsBo();

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
            campaignAddParticipantListRsBo.setStatus(CampaignAddParticipantListStatusEnum.ALREADY);
            campaignAddParticipantListRsBo.setMessage("已完成參與活動，不需重複參加。");
            return campaignAddParticipantListRsBo;
        }
        BigInteger participantListLimit = campaignDto.getParticipantListLimit();
        BigInteger participantListCount = BigInteger.valueOf(campaignParticipantListDtoList.size() + 1);
        if (participantListCount.compareTo(participantListLimit) > 0) {
            campaignAddParticipantListRsBo.setStatus(CampaignAddParticipantListStatusEnum.NOT_ELIGIBLE);
            campaignAddParticipantListRsBo.setMessage("超過參與人數限制。");
            return campaignAddParticipantListRsBo;
        }
        String participantListVersion = campaignDto.getParticipantListVersion();

        CampaignParticipantListDto campaignParticipantListDto = new CampaignParticipantListDto();
        campaignParticipantListDto.setCampaignNo(campaignNo);
        campaignParticipantListDto.setIpNo(ipNo);
        campaignParticipantListDto.setParticipantListVersion(participantListVersion);
        campaignParticipantListDao.saveCampaignParticipantList(campaignParticipantListDto);
        campaignAddParticipantListRsBo.setStatus(CampaignAddParticipantListStatusEnum.COMPLETED);
        campaignAddParticipantListRsBo.setMessage("完成活動參與。");
        return campaignAddParticipantListRsBo;
    }

}
