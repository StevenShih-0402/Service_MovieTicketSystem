package com.ctbcbank.navi.mid.campaign.management.service.campaign.query;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignConditionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignQueryServiceImpl implements CampaignQueryService {
    private final String CLASS_NAME = CampaignQueryServiceImpl.class.getSimpleName();
    private final CampaignDao campaignDao;

    @Override
    public CampaignQueryRsBo query(CampaignQueryRqBo campaignQueryRqBo) {
        CampaignQueryRsBo campaignQueryRsBo = new CampaignQueryRsBo();
        List<CampaignQueryRsBo.CampaignBo> campaignBoList = new ArrayList<>();
        QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
        queryCampaignConditionDto.setListingList(campaignQueryRqBo.getListingList());
        queryCampaignConditionDto.setCategoryList(campaignQueryRqBo.getCategoryList());
        queryCampaignConditionDto.setIsImmediate(campaignQueryRqBo.getIsImmediate());
        queryCampaignConditionDto.setEndStartDateTime(campaignQueryRqBo.getEndStartDateTime());
        queryCampaignConditionDto.setEndEndDateTime(campaignQueryRqBo.getEndEndDatetime());
        queryCampaignConditionDto.setParticipantTypeList(campaignQueryRqBo.getParticipantTypeList());
        List<CampaignDto> campaignDtoList = campaignDao.queryCampaign(queryCampaignConditionDto);
        campaignDtoList.forEach(x -> {
            CampaignQueryRsBo.CampaignBo campaignBo = CampaignQueryRsBo.CampaignBo.builder()
                    .id(x.getId())
                    .campaignNo(x.getCampaignNo())
                    .name(x.getName())
                    .description(x.getDescription())
                    .category(x.getCategory())
                    .isListing(x.getIsListing())
                    .isImmediate(x.getIsImmediate())
                    .immediateTransactionCode(x.getImmediateTransactionCode())
                    .createEmployeeNo(x.getCreateEmployeeNo())
                    .isParticipantList(x.getIsParticipantList())
                    .startDateTime(x.getStartDateTime())
                    .endDateTime(x.getEndDateTime())
                    .updateDttm(x.getUpdateDttm())
                    .createDttm(x.getCreateDttm())
                    .build();
            campaignBoList.add(campaignBo);
        });
        campaignQueryRsBo.setCampaignList(campaignBoList);
        return campaignQueryRsBo;
    }
}
