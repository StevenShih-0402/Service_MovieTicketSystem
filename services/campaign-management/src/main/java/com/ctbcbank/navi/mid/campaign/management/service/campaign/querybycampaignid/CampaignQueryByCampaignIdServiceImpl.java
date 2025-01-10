package com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignid;

import com.ctbcbank.navi.mid.campaign.management.dao.*;
import com.ctbcbank.navi.mid.campaign.management.dto.*;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignQueryByCampaignIdServiceImpl implements CampaignQueryByCampaignIdService {
    private final String CLASS_NAME = CampaignQueryByCampaignIdServiceImpl.class.getSimpleName();
    private final CampaignDao campaignDao;
    private final CampaignRuleGroupDao campaignRuleGroupDao;
    private final CampaignRuleGroupCouponDao campaignRuleGroupCouponDao;
    private final CampaignRuleSettingDao campaignRuleSettingDao;
    private final CampaignRuleDao campaignRuleDao;
    private final CampaignRuleExtraDao campaignRuleExtraDao;
    private final CampaignRuleSettingExtraDao campaignRuleSettingExtraDao;

    @Override
    public CampaignQueryByCampaignIdBoRs queryByCampaignId(CampaignQueryByCampaignIdBoRq campaignQueryByCampaignIdBoRq) {
        CampaignQueryByCampaignIdBoRs campaignQueryByCampaignIdBoRs = new CampaignQueryByCampaignIdBoRs();
        BigInteger campaignId = campaignQueryByCampaignIdBoRq.getCampaignId();
        log.info("[{}][queryByCampaignId][campaignId: {}]", CLASS_NAME, campaignId);
        QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
        queryCampaignConditionDto.setId(campaignId);
        List<CampaignDto> campaignDtoList = campaignDao.queryCampaign(queryCampaignConditionDto);
        if (CollectionUtils.isEmpty(campaignDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "CampaignId: " + campaignId);
        }
        campaignQueryByCampaignIdBoRs.setCampaign(getCampaignInfoBo(campaignDtoList.get(0)));
        campaignQueryByCampaignIdBoRs.setGroupNodes(getGroupNodeBos(campaignId));
        return campaignQueryByCampaignIdBoRs;
    }

    private CampaignQueryByCampaignIdBoRs.CampaignInfoBo getCampaignInfoBo(CampaignDto campaignDto) {
        CampaignQueryByCampaignIdBoRs.CampaignInfoBo campaignInfoBo = new CampaignQueryByCampaignIdBoRs.CampaignInfoBo();
        campaignInfoBo.setId(campaignDto.getId());
//        campaignInfoBo.setStatus(campaignDto.getStatus());
        campaignInfoBo.setCategory(campaignDto.getCategory());
        campaignInfoBo.setName(campaignDto.getName());
        campaignInfoBo.setDescription(campaignDto.getDescription());
//        campaignInfoBo.setIsImmediate(campaignDto.getIsImmediate());
        campaignInfoBo.setImmediateTransactionCode(campaignDto.getImmediateTransactionCode());
        campaignInfoBo.setStartDateTime(campaignDto.getStartDateTime());
        campaignInfoBo.setEndDateTime(campaignDto.getEndDateTime());
        campaignInfoBo.setCreateDttm(campaignDto.getCreateDttm());
        campaignInfoBo.setUpdateDttm(campaignDto.getUpdateDttm());
        return campaignInfoBo;
    }

    private List<CampaignQueryByCampaignIdBoRs.CampaignGroupNodeBo> getGroupNodeBos(BigInteger campaignId) {
        List<CampaignQueryByCampaignIdBoRs.CampaignGroupNodeBo> groupNodeBos = new ArrayList<>();
        QueryCampaignRuleGroupConditionDto queryCampaignRuleGroupConditionDto = new QueryCampaignRuleGroupConditionDto();
        queryCampaignRuleGroupConditionDto.setCampaignId(campaignId);
        List<CampaignRuleGroupDto> campaignRuleGroupDtoList = campaignRuleGroupDao.queryCampaignRuleGroup(queryCampaignRuleGroupConditionDto);
        if (CollectionUtils.isEmpty(campaignRuleGroupDtoList)) {
            log.info("[{}][getGroupNodeBos][campaignRuleGroupDtoList not found.]", CLASS_NAME);
            return groupNodeBos;
        }
        // 取出第一個節點(RULE_GROUP_PARENT_ID = NULL)，理論上只會有一筆
        List<CampaignRuleGroupDto> firstCampaignRuleGroupDtoList = campaignRuleGroupDtoList.stream().filter(x -> ObjectUtils.isEmpty(x.getRuleGroupParentId())).toList();
        if (CollectionUtils.isEmpty(firstCampaignRuleGroupDtoList)) {
            log.info("[{}][getGroupNodeBos][firstCampaignRuleGroupDtoList not found.]", CLASS_NAME);
            return groupNodeBos;
        }
        groupNodeBos.add(getGroupNodeBo(firstCampaignRuleGroupDtoList.get(0), campaignRuleGroupDtoList));
        return groupNodeBos;
    }

    private CampaignQueryByCampaignIdBoRs.CampaignGroupNodeBo getGroupNodeBo(CampaignRuleGroupDto campaignRuleGroupDto, List<CampaignRuleGroupDto> campaignRuleGroupDtoList) {
        CampaignQueryByCampaignIdBoRs.CampaignGroupNodeBo groupNodeBo = new CampaignQueryByCampaignIdBoRs.CampaignGroupNodeBo();
        groupNodeBo.setGroupNodeData(getGroupNodeDataBo(campaignRuleGroupDto));
        // 取得子節點
        List<CampaignRuleGroupDto> childCampaignRuleGroupDtoList = campaignRuleGroupDtoList.stream()
                .filter(x -> ObjectUtils.isNotEmpty(x.getRuleGroupParentId()) && x.getRuleGroupParentId().equals(campaignRuleGroupDto.getId())).toList();
        if (!CollectionUtils.isEmpty(childCampaignRuleGroupDtoList)) {
            List<CampaignQueryByCampaignIdBoRs.CampaignGroupNodeBo> childrenGroupNodes = new ArrayList<>();
            for (CampaignRuleGroupDto child : childCampaignRuleGroupDtoList) {
                childrenGroupNodes.add(getGroupNodeBo(child, campaignRuleGroupDtoList));
            }
            groupNodeBo.setChildrenGroupNodes(childrenGroupNodes);
        }
        return groupNodeBo;
    }

    private CampaignQueryByCampaignIdBoRs.CampaignGroupNodeDataBo getGroupNodeDataBo(CampaignRuleGroupDto campaignRuleGroupDto) {
        CampaignQueryByCampaignIdBoRs.CampaignGroupNodeDataBo groupNodeDataBo = new CampaignQueryByCampaignIdBoRs.CampaignGroupNodeDataBo();
        groupNodeDataBo.setMatchCount(campaignRuleGroupDto.getMatchCount());
        groupNodeDataBo.setRuleSettings(getRuleSettingBos(campaignRuleGroupDto));
        groupNodeDataBo.setCouponTemplateSettings(getCouponTemplateSettingBos(campaignRuleGroupDto));
        return groupNodeDataBo;
    }

    private List<CampaignQueryByCampaignIdBoRs.CampaignRuleSettingBo> getRuleSettingBos(CampaignRuleGroupDto campaignRuleGroupDto) {
        List<CampaignQueryByCampaignIdBoRs.CampaignRuleSettingBo> ruleSettingBos = new ArrayList<>();

        QueryCampaignRuleSettingConditionDto queryCampaignRuleSettingConditionDto = new QueryCampaignRuleSettingConditionDto();
        queryCampaignRuleSettingConditionDto.setRuleGroupId(campaignRuleGroupDto.getId());
        List<CampaignRuleSettingDto> campaignRuleSettingDtoList = campaignRuleSettingDao.queryCampaignRuleSetting(queryCampaignRuleSettingConditionDto);
        for (CampaignRuleSettingDto dto : campaignRuleSettingDtoList) {
            CampaignQueryByCampaignIdBoRs.CampaignRuleSettingBo ruleSettingBo = new CampaignQueryByCampaignIdBoRs.CampaignRuleSettingBo();
            ruleSettingBo.setId(dto.getId());
            ruleSettingBo.setRuleSetting(true);
            ruleSettingBo.setTransactionCode(dto.getTransactionCode());
            ruleSettingBo.setFieldName(dto.getFieldName());
            ruleSettingBo.setRuleName(dto.getRuleName());
            ruleSettingBo.setRuleValue(dto.getRuleValue());
            ruleSettingBo.setRuleType(dto.getRuleType());
            ruleSettingBos.add(ruleSettingBo);
        }

        QueryCampaignRuleSettingExtraConditionDto queryCampaignRuleSettingExtraConditionDto = new QueryCampaignRuleSettingExtraConditionDto();
        queryCampaignRuleSettingExtraConditionDto.setRuleGroupId(campaignRuleGroupDto.getId());
        List<CampaignRuleSettingExtraDto> campaignRuleSettingExtraDtoList = campaignRuleSettingExtraDao.queryCampaignRuleSettingExtra(queryCampaignRuleSettingExtraConditionDto);
        for (CampaignRuleSettingExtraDto dto : campaignRuleSettingExtraDtoList) {
            CampaignQueryByCampaignIdBoRs.CampaignRuleSettingBo ruleSettingBo = new CampaignQueryByCampaignIdBoRs.CampaignRuleSettingBo();
            ruleSettingBo.setId(dto.getId());
            ruleSettingBo.setRuleSetting(false);
            ruleSettingBo.setTransactionCode(dto.getTransactionCode());
            ruleSettingBo.setFieldName(dto.getFieldName());
            ruleSettingBo.setRuleName(dto.getRuleName());
            ruleSettingBo.setRuleValue(dto.getRuleValue());
            ruleSettingBo.setRuleType(dto.getRuleType());
            ruleSettingBos.add(ruleSettingBo);
        }
        return ruleSettingBos;
    }

    private List<CampaignQueryByCampaignIdBoRs.CampaignCouponTemplateSettingBo> getCouponTemplateSettingBos(CampaignRuleGroupDto campaignRuleGroupDto) {
        List<CampaignQueryByCampaignIdBoRs.CampaignCouponTemplateSettingBo> couponTemplateSettingBos = new ArrayList<>();
        if (StringUtils.equalsAnyIgnoreCase(campaignRuleGroupDto.getHasCoupon(), "N")) {
            return couponTemplateSettingBos;
        }
        QueryCampaignRuleGroupCouponConditionDto queryCampaignRuleGroupCouponConditionDto = new QueryCampaignRuleGroupCouponConditionDto();
        queryCampaignRuleGroupCouponConditionDto.setRuleGroupId(campaignRuleGroupDto.getId());
        List<CampaignRuleGroupCouponDto> campaignRuleGroupCouponDtoList = campaignRuleGroupCouponDao.queryCampaignRuleGroupCoupon(queryCampaignRuleGroupCouponConditionDto);
        for (CampaignRuleGroupCouponDto dto : campaignRuleGroupCouponDtoList) {
            CampaignQueryByCampaignIdBoRs.CampaignCouponTemplateSettingBo couponTemplateSettingBo = new CampaignQueryByCampaignIdBoRs.CampaignCouponTemplateSettingBo();
            couponTemplateSettingBo.setCouponTemplateNo(dto.getCouponTemplateNo());
            couponTemplateSettingBos.add(couponTemplateSettingBo);
        }
        return couponTemplateSettingBos;
    }


}
