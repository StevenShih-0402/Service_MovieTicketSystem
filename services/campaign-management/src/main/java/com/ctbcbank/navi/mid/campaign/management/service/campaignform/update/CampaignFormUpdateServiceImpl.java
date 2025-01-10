package com.ctbcbank.navi.mid.campaign.management.service.campaignform.update;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormHistoryDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignFormParticipantListDao;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignFormConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignFormDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.CampaignFormHistoryDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.converter.CampaignFormHistoryDtoConverter;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformparticipantlist.CampaignFormParticipantListDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormUpdateServiceImpl implements CampaignFormUpdateService {

    private final String CLASS_NAME = CampaignFormUpdateServiceImpl.class.getSimpleName();
    private final CampaignFormDao campaignFormDao;
    private final CampaignFormHistoryDao campaignFormHistoryDao;
    private final CampaignFormParticipantListDao campaignFormParticipantListDao;

    @Override
    public CampaignFormUpdateRsBo update(CampaignFormUpdateRqBo campaignFormUpdateRqBo) {
        String campaignFormNo = campaignFormUpdateRqBo.getCampaignFormNo();
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
        String campaignNo = campaignFormDto.getCampaignNo();

        if (campaignFormUpdateRqBo.getIsUpdateCampaignFormInfo()) {
            log.info("[{}][update][UpdateCampaignFormInfo Before: {}]", CLASS_NAME, campaignFormDto);
            getUpdateCampaignFormDto(campaignFormDto, campaignFormUpdateRqBo.getCampaignFormInfo());
            campaignFormDto = campaignFormDao.saveCampaignForm(campaignFormDto);
            log.info("[{}][update][UpdateCampaignFormInfo After: {}]", CLASS_NAME, campaignFormDto);
        }

        if (campaignFormUpdateRqBo.getIsUpdateCampaignFormGroupNode()) {
            log.info("[{}][update][UpdateCampaignFormGroupNode Before: {}]", CLASS_NAME, campaignFormDto);
            campaignFormDto.setGroupNodeData(campaignFormUpdateRqBo.getGroupNodeData());
            campaignFormDto = campaignFormDao.saveCampaignForm(campaignFormDto);
            log.info("[{}][update][UpdateCampaignFormGroupNode After: {}]", CLASS_NAME, campaignFormDto);
        }

        if (campaignFormUpdateRqBo.getIsUpdateCampaignFormParticipantList()) {
            log.info("[{}][update][UpdateCampaignFormParticipantList Before: {}]", CLASS_NAME, campaignFormDto);
            CampaignParticipantTypeEnum participantType = campaignFormUpdateRqBo.getParticipantType();
            campaignFormDto.setParticipantType(participantType);
            switch (participantType) {
                case FREE -> {
                    campaignFormDto.setCustomerListNo(null);
                    campaignFormDto.setParticipantListVersion(null);
                    campaignFormDto.setParticipantListLimit(null);
                }
                case CUSTOMER_LIST -> {
                    campaignFormDto.setCustomerListNo(campaignFormUpdateRqBo.getCustomerListNo());
                    // 給予新參與名單版本號
                    campaignFormDto.setParticipantListVersion(UUIDUtils.getUUID());
                    campaignFormDto.setParticipantListLimit(null);
                }
                case ONLINE -> {
                    campaignFormDto.setCustomerListNo(null);
                    // 給予新參與名單版本號
                    campaignFormDto.setParticipantListVersion(UUIDUtils.getUUID());
                    campaignFormDto.setParticipantListLimit(campaignFormUpdateRqBo.getParticipantListLimit());
                }
            }

            campaignFormDto = campaignFormDao.saveCampaignForm(campaignFormDto);
            log.info("[{}][update][UpdateCampaignFormParticipantList After: {}]", CLASS_NAME, campaignFormDto);

        }

        // 新增紀錄
        CampaignFormHistoryDto campaignFormHistoryDto = CampaignFormHistoryDtoConverter.parse(campaignFormDto, campaignFormUpdateRqBo.getUpdateEmployeeNo());
        campaignFormHistoryDao.saveCampaignFormHistory(campaignFormHistoryDto);

        CampaignFormUpdateRsBo campaignFormUpdateRsBo = new CampaignFormUpdateRsBo();
        campaignFormUpdateRsBo.setCampaignFormInfo(getCampaignFormInfoBo(campaignFormDto));
        return campaignFormUpdateRsBo;
    }

    private void getUpdateCampaignFormDto(CampaignFormDto campaignFormDto, CampaignFormUpdateRqBo.CampaignFormInfoBo campaignFormInfoBo) {
        campaignFormDto.setName(campaignFormInfoBo.getName());
        campaignFormDto.setDescription(campaignFormInfoBo.getDescription());
        campaignFormDto.setCategory(campaignFormInfoBo.getCategory());
        campaignFormDto.setIsImmediate(campaignFormInfoBo.getIsImmediate());
        campaignFormDto.setImmediateTransactionCode(campaignFormInfoBo.getImmediateTransactionCode());
        campaignFormDto.setStartDateTime(campaignFormInfoBo.getStartDateTime());
        campaignFormDto.setEndDateTime(campaignFormInfoBo.getEndDateTime());
        campaignFormDto.setIsListing(campaignFormInfoBo.getIsListing());
    }

    private CampaignFormUpdateRsBo.CampaignFormInfoBo getCampaignFormInfoBo(CampaignFormDto campaignFormDto) {
        CampaignFormUpdateRsBo.CampaignFormInfoBo campaignFormInfoBo = new CampaignFormUpdateRsBo.CampaignFormInfoBo();
        campaignFormInfoBo.setCampaignFormNo(campaignFormDto.getCampaignFormNo());
        campaignFormInfoBo.setCampaignNo(campaignFormDto.getCampaignNo());
        campaignFormInfoBo.setName(campaignFormDto.getName());
        campaignFormInfoBo.setDescription(campaignFormDto.getDescription());
        campaignFormInfoBo.setCategory(campaignFormDto.getCategory());
        campaignFormInfoBo.setIsImmediate(campaignFormDto.getIsImmediate());
        campaignFormInfoBo.setImmediateTransactionCode(campaignFormDto.getImmediateTransactionCode());
        campaignFormInfoBo.setStartDateTime(campaignFormDto.getStartDateTime());
        campaignFormInfoBo.setEndDateTime(campaignFormDto.getEndDateTime());
        campaignFormInfoBo.setCampaignFormType(campaignFormDto.getCampaignFormType());
        campaignFormInfoBo.setIsListing(campaignFormDto.getIsListing());
        return campaignFormInfoBo;
    }

}
