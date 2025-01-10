package com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignno;

import com.ctbcbank.navi.mid.campaign.management.dao.*;
import com.ctbcbank.navi.mid.campaign.management.dto.*;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignid.CampaignQueryByCampaignIdBoRs;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybycampaignid.CampaignQueryByCampaignIdServiceImpl;
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
public class CampaignQueryByCampaignNoServiceImpl implements CampaignQueryByCampaignNoService {
    private final String CLASS_NAME = CampaignQueryByCampaignNoServiceImpl.class.getSimpleName();
    private final CampaignDao campaignDao;
    private final CampaignRuleGroupDao campaignRuleGroupDao;
    private final CampaignRuleGroupCouponDao campaignRuleGroupCouponDao;
    private final CampaignRuleSettingDao campaignRuleSettingDao;
    private final CampaignRuleSettingExtraDao campaignRuleSettingExtraDao;

    @Override
    public CampaignQueryByCampaignNoRsBo queryByCampaignNo(CampaignQueryByCampaignNoRqBo campaignQueryByCampaignNoRqBo) {
        String campaignNo = campaignQueryByCampaignNoRqBo.getCampaignNo();
        QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
        queryCampaignConditionDto.setCampaignNo(campaignNo);
        List<CampaignDto> campaignDtoList = campaignDao.queryCampaign(queryCampaignConditionDto);
        if (CollectionUtils.isEmpty(campaignDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "campaignNo: " + campaignNo);
        }
        CampaignDto campaignDto = campaignDtoList.get(0);
        BigInteger campaignId = campaignDto.getId();
        CampaignQueryByCampaignNoRsBo campaignQueryByCampaignNoRsBo = new CampaignQueryByCampaignNoRsBo();
        campaignQueryByCampaignNoRsBo.setCampaignInfoBo(getCampaignInfoBo(campaignDto));
        campaignQueryByCampaignNoRsBo.setGroupNodeBos(getGroupNodeBos(campaignId));
        return campaignQueryByCampaignNoRsBo;
    }

    private CampaignQueryByCampaignNoRsBo.CampaignInfoBo getCampaignInfoBo(CampaignDto campaignDto) {
        CampaignQueryByCampaignNoRsBo.CampaignInfoBo campaignInfoBo = new CampaignQueryByCampaignNoRsBo.CampaignInfoBo();
        campaignInfoBo.setId(campaignDto.getId());
        campaignInfoBo.setCampaignNo(campaignDto.getCampaignNo());
        campaignInfoBo.setIsListing(campaignDto.getIsListing());
        campaignInfoBo.setCategory(campaignDto.getCategory());
        campaignInfoBo.setName(campaignDto.getName());
        campaignInfoBo.setDescription(campaignDto.getDescription());
        campaignInfoBo.setIsImmediate(campaignDto.getIsImmediate());
        campaignInfoBo.setImmediateTransactionCode(campaignDto.getImmediateTransactionCode());
        campaignInfoBo.setStartDateTime(campaignDto.getStartDateTime());
        campaignInfoBo.setEndDateTime(campaignDto.getEndDateTime());
        campaignInfoBo.setCreateEmployeeNo(campaignDto.getCreateEmployeeNo());
        campaignInfoBo.setIsParticipantList(campaignDto.getIsParticipantList());
        campaignInfoBo.setCustomerListNo(campaignDto.getCustomerListNo());
        campaignInfoBo.setParticipantType(campaignDto.getParticipantType());
        campaignInfoBo.setParticipantListLimit(campaignDto.getParticipantListLimit());
        campaignInfoBo.setCreateDttm(campaignDto.getCreateDttm());
        campaignInfoBo.setUpdateDttm(campaignDto.getUpdateDttm());
        return campaignInfoBo;
    }

    private List<CampaignQueryByCampaignNoRsBo.CampaignGroupNodeBo> getGroupNodeBos(BigInteger campaignId) {
        List<CampaignQueryByCampaignNoRsBo.CampaignGroupNodeBo> groupNodeBos = new ArrayList<>();
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

    private CampaignQueryByCampaignNoRsBo.CampaignGroupNodeBo getGroupNodeBo(CampaignRuleGroupDto campaignRuleGroupDto, List<CampaignRuleGroupDto> campaignRuleGroupDtoList) {
        CampaignQueryByCampaignNoRsBo.CampaignGroupNodeBo groupNodeBo = new CampaignQueryByCampaignNoRsBo.CampaignGroupNodeBo();
        groupNodeBo.setGroupNodeData(getGroupNodeDataBo(campaignRuleGroupDto));

        // 取得子節點
        List<CampaignRuleGroupDto> childCampaignRuleGroupDtoList = campaignRuleGroupDtoList.stream()
                .filter(x -> ObjectUtils.isNotEmpty(x.getRuleGroupParentId()) && x.getRuleGroupParentId().equals(campaignRuleGroupDto.getId())).toList();
        if (!CollectionUtils.isEmpty(childCampaignRuleGroupDtoList)) {
            List<CampaignQueryByCampaignNoRsBo.CampaignGroupNodeBo> childrenGroupNodes = new ArrayList<>();
            for (CampaignRuleGroupDto child : childCampaignRuleGroupDtoList) {
                childrenGroupNodes.add(getGroupNodeBo(child, campaignRuleGroupDtoList));
            }
            groupNodeBo.setChildrenGroupNodes(childrenGroupNodes);
        }
        return groupNodeBo;
    }

    private CampaignQueryByCampaignNoRsBo.CampaignGroupNodeDataBo getGroupNodeDataBo(CampaignRuleGroupDto campaignRuleGroupDto) {
        CampaignQueryByCampaignNoRsBo.CampaignGroupNodeDataBo groupNodeDataBo = new CampaignQueryByCampaignNoRsBo.CampaignGroupNodeDataBo();
        groupNodeDataBo.setMatchCount(campaignRuleGroupDto.getMatchCount());
        groupNodeDataBo.setRuleSettings(getRuleSettingBos(campaignRuleGroupDto));
        groupNodeDataBo.setCouponTemplateSettings(getCouponTemplateSettingBos(campaignRuleGroupDto));
        return groupNodeDataBo;
    }

    private List<CampaignQueryByCampaignNoRsBo.CampaignRuleSettingBo> getRuleSettingBos(CampaignRuleGroupDto campaignRuleGroupDto) {
        List<CampaignQueryByCampaignNoRsBo.CampaignRuleSettingBo> ruleSettingBos = new ArrayList<>();
        QueryCampaignRuleSettingConditionDto queryCampaignRuleSettingConditionDto = new QueryCampaignRuleSettingConditionDto();
        queryCampaignRuleSettingConditionDto.setRuleGroupId(campaignRuleGroupDto.getId());
        List<CampaignRuleSettingDto> campaignRuleSettingDtoList = campaignRuleSettingDao.queryCampaignRuleSetting(queryCampaignRuleSettingConditionDto);
        for (CampaignRuleSettingDto dto : campaignRuleSettingDtoList) {
            CampaignQueryByCampaignNoRsBo.CampaignRuleSettingBo ruleSettingBo = new CampaignQueryByCampaignNoRsBo.CampaignRuleSettingBo();
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
            CampaignQueryByCampaignNoRsBo.CampaignRuleSettingBo ruleSettingBo = new CampaignQueryByCampaignNoRsBo.CampaignRuleSettingBo();
            ruleSettingBo.setTransactionCode(dto.getTransactionCode());
            ruleSettingBo.setFieldName(dto.getFieldName());
            ruleSettingBo.setRuleName(dto.getRuleName());
            ruleSettingBo.setRuleValue(dto.getRuleValue());
            ruleSettingBo.setRuleType(dto.getRuleType());
            ruleSettingBos.add(ruleSettingBo);
        }

        return ruleSettingBos;
    }

    private List<CampaignQueryByCampaignNoRsBo.CampaignCouponTemplateSettingBo> getCouponTemplateSettingBos(CampaignRuleGroupDto campaignRuleGroupDto) {
        List<CampaignQueryByCampaignNoRsBo.CampaignCouponTemplateSettingBo> couponTemplateSettingBos = new ArrayList<>();
        if (StringUtils.equalsAnyIgnoreCase(campaignRuleGroupDto.getHasCoupon(), "N")) {
            return couponTemplateSettingBos;
        }
        QueryCampaignRuleGroupCouponConditionDto queryCampaignRuleGroupCouponConditionDto = new QueryCampaignRuleGroupCouponConditionDto();
        queryCampaignRuleGroupCouponConditionDto.setRuleGroupId(campaignRuleGroupDto.getId());
        List<CampaignRuleGroupCouponDto> campaignRuleGroupCouponDtoList = campaignRuleGroupCouponDao.queryCampaignRuleGroupCoupon(queryCampaignRuleGroupCouponConditionDto);
        for (CampaignRuleGroupCouponDto dto : campaignRuleGroupCouponDtoList) {
            CampaignQueryByCampaignNoRsBo.CampaignCouponTemplateSettingBo couponTemplateSettingBo = new CampaignQueryByCampaignNoRsBo.CampaignCouponTemplateSettingBo();
            couponTemplateSettingBo.setCouponTemplateNo(dto.getCouponTemplateNo());
            couponTemplateSettingBos.add(couponTemplateSettingBo);
        }
        return couponTemplateSettingBos;
    }
}
