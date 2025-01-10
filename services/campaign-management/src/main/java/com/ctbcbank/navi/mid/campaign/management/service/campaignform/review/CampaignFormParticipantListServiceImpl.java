package com.ctbcbank.navi.mid.campaign.management.service.campaignform.review;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCustomerListDetailDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignParticipantListDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignDto;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail.CampaignCustomerListDetailDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail.QueryCampaignCustomerListDetailConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignparticipantlist.CampaignParticipantListDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantListSourceTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormParticipantListServiceImpl implements CampaignFormParticipantListService {
    private final String CLASS_NAME = CampaignFormParticipantListServiceImpl.class.getSimpleName();
    private final CampaignFormDao campaignFormDao;
    private final CampaignDao campaignDao;
    private final CampaignParticipantListDao campaignParticipantListDao;
    private final CampaignCustomerListDetailDao campaignCustomerListDetailDao;

    @Override
    @Async
    @Transactional
    public void asyncProcessParticipantList(CampaignFormReviewRqBo campaignFormReviewRqBo) {

        // 取得活動表單
        String campaignFormNo = campaignFormReviewRqBo.getCampaignFormNo();
        log.info("[{}][asyncProcessParticipantList][campaignFormNo: {}]", CLASS_NAME, campaignFormNo);
        QueryCampaignFormConditionDto queryCampaignFormConditionDto = new QueryCampaignFormConditionDto();
        queryCampaignFormConditionDto.setCampaignFormNo(campaignFormNo);
        List<CampaignFormDto> campaignFormDtoList = campaignFormDao.queryCampaignForm(queryCampaignFormConditionDto);
        if (CollectionUtils.isEmpty(campaignFormDtoList)) {
            log.error("[{}][asyncProcessParticipantList][campaignFormDtoList is empty.]", CLASS_NAME);
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "CampaignFormNo: " + campaignFormNo);
        }
        if (campaignFormDtoList.size() > 1) {
            log.error("[{}][asyncProcessParticipantList][campaignFormDtoList size > 1.]", CLASS_NAME);
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "CampaignFormNo: " + campaignFormNo);
        }
        CampaignFormDto campaignFormDto = campaignFormDtoList.get(0);
        Boolean campaignFormIsParticipantList = campaignFormDto.getIsParticipantList();
        String campaignFormCustomerListNo = campaignFormDto.getCustomerListNo();
        String campaignFormParticipantListVersion = campaignFormDto.getParticipantListVersion();
        CampaignParticipantTypeEnum campaignFormParticipantType = campaignFormDto.getParticipantType();
        BigInteger campaignFormParticipantListLimit = campaignFormDto.getParticipantListLimit();

        // 取得活動
        String campaignNo = campaignFormDto.getCampaignNo();
        log.info("[{}][asyncProcessParticipantList][campaignNo: {}]", CLASS_NAME, campaignNo);
        QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
        queryCampaignConditionDto.setCampaignNo(campaignNo);
        List<CampaignDto> campaignDtoList = campaignDao.queryCampaign(queryCampaignConditionDto);
        if (CollectionUtils.isEmpty(campaignDtoList)) {
            log.error("[{}][asyncProcessParticipantList][campaignDtoList is empty.]", CLASS_NAME);
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "CampaignFormNo: " + campaignFormNo);
        }
        if (campaignFormDtoList.size() > 1) {
            log.error("[{}][asyncProcessParticipantList][campaignFormDtoList size > 1.]", CLASS_NAME);
            throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "CampaignFormNo: " + campaignFormNo);
        }
        CampaignDto campaignDto = campaignDtoList.get(0);

        log.info("[{}][asyncProcessParticipantList][ParticipantType: {}]", CLASS_NAME, campaignFormParticipantType);
        if (campaignFormParticipantType.compareTo(CampaignParticipantTypeEnum.FREE) == 0) {
            String campaignParticipantListVersion = campaignDto.getParticipantListVersion();
            campaignDto.setParticipantType(campaignFormParticipantType);
            campaignDto.setIsParticipantList(false);
            campaignDto.setCustomerListNo(null);
            campaignDto.setParticipantListLimit(null);
            campaignDto.setParticipantListVersion(null);
            updateCampaign(campaignDto);
            // 刪除參與名單 By 活動的參與名單版本號
            deleteCampaignParticipantListByParticipantListVersion(campaignParticipantListVersion);
            return;
        }

        if (campaignFormParticipantType.compareTo(CampaignParticipantTypeEnum.ONLINE) == 0) {
            String campaignParticipantListVersion = campaignDto.getParticipantListVersion();
            campaignDto.setParticipantType(campaignFormParticipantType);
            campaignDto.setIsParticipantList(true);
            campaignDto.setCustomerListNo(null);
            campaignDto.setParticipantListLimit(campaignFormParticipantListLimit);
            campaignDto.setParticipantListVersion(campaignFormParticipantListVersion);
            updateCampaign(campaignDto);
            // 刪除參與名單 By 活動的參與名單版本號
            deleteCampaignParticipantListByParticipantListVersion(campaignParticipantListVersion);
            return;
        }

        // 刪除參與名單 By 活動表單的參與名單版本號
        deleteCampaignParticipantListByParticipantListVersion(campaignFormParticipantListVersion);

        // 查詢客戶名單清單細項
        QueryCampaignCustomerListDetailConditionDto queryCampaignCustomerListDetailConditionDto = new QueryCampaignCustomerListDetailConditionDto();
        queryCampaignCustomerListDetailConditionDto.setCustomerListNo(campaignFormCustomerListNo);
        List<CampaignCustomerListDetailDto> campaignCustomerListDetailDtoList = campaignCustomerListDetailDao.queryCampaignCustomerListDetail(queryCampaignCustomerListDetailConditionDto);
        List<CampaignParticipantListDto> campaignParticipantListDtoList = getCampaignParticipantListDtoList(campaignFormDto, campaignCustomerListDetailDtoList);
        insertCampaignParticipantList(campaignFormDto, campaignDto, campaignParticipantListDtoList);

        campaignDto.setParticipantType(campaignFormParticipantType);
        campaignDto.setIsParticipantList(true);
        campaignDto.setCustomerListNo(campaignFormCustomerListNo);
        campaignDto.setParticipantListLimit(null);
        campaignDto.setParticipantListVersion(campaignFormParticipantListVersion);
        updateCampaign(campaignDto);
    }


    private void updateCampaign(CampaignDto campaignDto) {
        log.info("[{}][updateCampaign][Update campaignDto Before: {}]", CLASS_NAME, campaignDto);
        campaignDto = campaignDao.saveCampaign(campaignDto);
        log.info("[{}][update][Update campaignDto After: {}]", CLASS_NAME, campaignDto);
    }

    private void deleteCampaignParticipantListByParticipantListVersion(String participantListVersion) {
        if (StringUtils.isBlank(participantListVersion)) {
            log.info("[{}][deleteCampaignParticipantListByParticipantListVersion][participantListVersion is blank]", CLASS_NAME);
            return;
        }
        log.info("[{}][deleteCampaignParticipantListByParticipantListVersion][participantListVersion: {}]", CLASS_NAME, participantListVersion);
        int campaignParticipantListDelete = campaignParticipantListDao.deleteByParticipantListVersion(participantListVersion);
        log.info("[{}][deleteCampaignParticipantListByParticipantListVersion][campaignParticipantListDelete: {}]", CLASS_NAME, campaignParticipantListDelete);
    }

    List<CampaignParticipantListDto> getCampaignParticipantListDtoList(CampaignFormDto campaignFormDto, List<CampaignCustomerListDetailDto> campaignCustomerListDetailDtoList) {
        List<CampaignParticipantListDto> campaignParticipantListDtoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignCustomerListDetailDtoList)) {
            return campaignParticipantListDtoList;
        }
        campaignParticipantListDtoList = campaignCustomerListDetailDtoList.stream().map(x -> {
            CampaignParticipantListDto campaignParticipantListDto = new CampaignParticipantListDto();
            campaignParticipantListDto.setCampaignNo(campaignFormDto.getCampaignNo());
            campaignParticipantListDto.setIpNo(x.getChosenIpNo());
            campaignParticipantListDto.setSourceType(CampaignParticipantListSourceTypeEnum.CUSTOMER_LIST);
            campaignParticipantListDto.setCustomerListNo(campaignFormDto.getCustomerListNo());
            campaignParticipantListDto.setParticipantListVersion(campaignFormDto.getParticipantListVersion());
            return campaignParticipantListDto;
        }).toList();
        return campaignParticipantListDtoList;
    }

    private void insertCampaignParticipantList(CampaignFormDto campaignFormDto, CampaignDto campaignDto, List<CampaignParticipantListDto> campaignParticipantListDtoList) {
        if (CollectionUtils.isEmpty(campaignParticipantListDtoList)) {
            log.info("[{}][insertCampaignParticipantList][campaignParticipantListDtoList is empty.]", CLASS_NAME);
            return;
        }
        log.info("[{}][insertCampaignParticipantList][campaignParticipantListDtoList size: {}]", CLASS_NAME, campaignParticipantListDtoList.size());
        // 批次insert
        campaignParticipantListDao.saveCampaignParticipantList(campaignParticipantListDtoList, 100);
        String campaignParticipantListVersion = campaignDto.getParticipantListVersion();
        campaignDto.setIsParticipantList(true);
        campaignDto.setCustomerListNo(campaignFormDto.getCustomerListNo());
        campaignDto.setParticipantListVersion(campaignFormDto.getParticipantListVersion());
        updateCampaign(campaignDto);
        log.info("[{}][insertCampaignParticipantList][updateCampaign success.]", CLASS_NAME);

        deleteCampaignParticipantListByParticipantListVersion(campaignParticipantListVersion);
        log.info("[{}][insertCampaignParticipantList][deleteCampaignParticipantListByParticipantListVersion success.]", CLASS_NAME);
    }


}
