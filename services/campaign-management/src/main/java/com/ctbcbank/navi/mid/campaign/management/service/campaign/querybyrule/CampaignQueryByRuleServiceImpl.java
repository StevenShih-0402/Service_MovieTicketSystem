package com.ctbcbank.navi.mid.campaign.management.service.campaign.querybyrule;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignDto;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleSettingDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignConditionDto;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.query.CampaignQueryServiceImpl;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignQueryByRuleServiceImpl implements CampaignQueryByRuleService {
    private final String CLASS_NAME = CampaignQueryByRuleServiceImpl.class.getSimpleName();
    private final CampaignDao campaignDao;

    @Override
    public CampaignQueryByRuleRsBo queryByRule(CampaignQueryByRuleRqBo campaignQueryByRuleRqBo) {
        List<BigInteger> campaignIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(campaignQueryByRuleRqBo.getCampaignName())) {
            QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
            queryCampaignConditionDto.setCampaignNameLike(campaignQueryByRuleRqBo.getCampaignName());
            List<CampaignDto> campaignDtoList = campaignDao.queryCampaign(queryCampaignConditionDto);
            campaignIdList = campaignDtoList.stream().map(CampaignDto::getId).toList();
            log.info("[{}][queryByRule][campaignIdList : {}]", CLASS_NAME, campaignIdList);
        }
        Integer number = campaignQueryByRuleRqBo.getNumber();
        Integer size = campaignQueryByRuleRqBo.getSize();
        List<CampaignRuleSettingDto> campaignRuleSettingDtoList = campaignQueryByRuleRqBo.getRuleList().stream().map(x -> {
            CampaignRuleSettingDto campaignRuleSettingDto = new CampaignRuleSettingDto();
            campaignRuleSettingDto.setTransactionCode(x.getTransactionCode().name());
            campaignRuleSettingDto.setRuleName(x.getRuleName().name());
            campaignRuleSettingDto.setRuleValue(x.getRuleValue());
            campaignRuleSettingDto.setRuleType(x.getRuleType().name());
            return campaignRuleSettingDto;
        }).toList();
        Page<BigInteger> queryResult = campaignDao.queryCampaignByRule(campaignIdList, campaignRuleSettingDtoList, number, size);
        List<BigInteger> queryCampangiIdList = queryResult.getContent();
        List<CampaignDto> campaignDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(queryCampangiIdList)) {
            QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
            queryCampaignConditionDto.setIdList(queryCampangiIdList);
            campaignDtoList = campaignDao.queryCampaign(queryCampaignConditionDto);
        }

        CampaignQueryByRuleRsBo campaignQueryByRuleRsBo = new CampaignQueryByRuleRsBo();
        campaignQueryByRuleRsBo.setTotalElements(queryResult.getTotalElements());
        campaignQueryByRuleRsBo.setTotalPages(queryResult.getTotalPages());
        campaignQueryByRuleRsBo.setNumber(queryResult.getNumber());
        campaignQueryByRuleRsBo.setSize(queryResult.getSize());
        campaignQueryByRuleRsBo.setCampaignList(getCampaignBoList(campaignDtoList));
        return campaignQueryByRuleRsBo;
    }

    private List<CampaignQueryByRuleRsBo.CampaignBo> getCampaignBoList(List<CampaignDto> campaignDtoList) {
        List<CampaignQueryByRuleRsBo.CampaignBo> campaignBoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignDtoList)) {
            return campaignBoList;
        }
        campaignBoList = campaignDtoList.stream().map(x -> {
            CampaignQueryByRuleRsBo.CampaignBo campaignBo = new CampaignQueryByRuleRsBo.CampaignBo();
            campaignBo.setId(x.getId());
            campaignBo.setCampaignNo(x.getCampaignNo());
            campaignBo.setIsListing(x.getIsListing());
            campaignBo.setCategory(x.getCategory());
            campaignBo.setName(x.getName());
            campaignBo.setDescription(x.getDescription());
            campaignBo.setIsImmediate(x.getIsImmediate());
            campaignBo.setImmediateTransactionCode(x.getImmediateTransactionCode());
            campaignBo.setStartDateTime(x.getStartDateTime());
            campaignBo.setEndDateTime(x.getEndDateTime());
            campaignBo.setCreateEmployeeNo(x.getCreateEmployeeNo());
            campaignBo.setCreateDttm(x.getCreateDttm());
            campaignBo.setUpdateDttm(x.getUpdateDttm());
            return campaignBo;
        }).toList();
        return campaignBoList;
    }


}
