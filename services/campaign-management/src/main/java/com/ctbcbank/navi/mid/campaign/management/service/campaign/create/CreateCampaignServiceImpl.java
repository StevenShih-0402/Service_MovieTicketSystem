package com.ctbcbank.navi.mid.campaign.management.service.campaign.create;

import com.ctbcbank.navi.mid.campaign.management.dao.*;
import com.ctbcbank.navi.mid.campaign.management.dto.*;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignStatus;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
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
public class CreateCampaignServiceImpl implements CreateCampaignService {
    private final String CLASS_NAME = CreateCampaignServiceImpl.class.getSimpleName();

    private final CampaignDao campaignDao;
    private final CampaignRuleSettingDao campaignRuleSettingDao;
    private final CampaignRuleGroupCouponDao campaignRuleGroupCouponDao;
    private final CampaignRuleTransactionCodeDao campaignRuleTransactionCodeDao;
    private final CampaignRuleSettingExtraDao campaignRuleSettingExtraDao;

    @Override
    @Transactional
    public CampaignCreateRsBo create(CampaignCreateRqBo campaignCreateRqBo) {
        CampaignCreateRsBo campaignCreateRsBo = new CampaignCreateRsBo();
        CampaignDto campaignDto = new CampaignDto();
        BeanUtils.copyProperties(campaignCreateRqBo.getCampaignInfo(), campaignDto);
        // 預設為草稿
//        if (ObjectUtils.isEmpty(campaignDto.getStatus())) {
//            campaignDto.setStatus(CampaignStatus.DRAFT.getCode());
//        }
        CampaignDto saveCampaignDto = campaignDao.saveCampaign(campaignDto);
        BigInteger campaignId = saveCampaignDto.getId();
        campaignCreateRsBo.setCampaignId(campaignId);
        List<String> transactionCodeList = new ArrayList<>();
        if (campaignCreateRqBo.getGroupNodes() != null && !CollectionUtils.isEmpty(campaignCreateRqBo.getGroupNodes())) {
            // 第一層的父節點為NULL
            parseGroupNode(saveCampaignDto, null, campaignCreateRqBo.getGroupNodes().get(0), transactionCodeList);
        }
        log.info("[{}][create][transactionCodeList: {}]", CLASS_NAME, transactionCodeList);
        transactionCodeList.forEach(x -> {
            CampaignRuleTransactionCodeDto campaignRuleTransactionCodeDto = new CampaignRuleTransactionCodeDto();
            campaignRuleTransactionCodeDto.setCampaignId(campaignId);
            campaignRuleTransactionCodeDto.setTransactionCode(x);
            campaignRuleTransactionCodeDao.saveCampaignRuleTransactionCode(campaignRuleTransactionCodeDto);
        });
        return campaignCreateRsBo;
    }

    public void parseGroupNode(CampaignDto campaignDto, BigInteger parentGroupNodeId, CampaignCreateRqBo.CampaignGroupNodeBo node, List<String> transactionCodeList) {
        if (node == null) {
            return;
        }
        // 取節點的規則設定、優惠券設定、符合條件數量
        CampaignCreateRqBo.CampaignGroupNodeDataBo campaignGroupNodeDataBo = node.getGroupNodeData();
        List<CampaignCreateRqBo.CampaignRuleSettingBo> ruleSettings = campaignGroupNodeDataBo.getRuleSettings();
        List<CampaignCreateRqBo.CampaignCouponTemplateSettingBo> couponSettings = campaignGroupNodeDataBo.getCouponTemplateSettings();
        long matchCount = campaignGroupNodeDataBo.getMatchCount().longValue();

        CampaignRuleGroupDto campaignRuleGroupDto = new CampaignRuleGroupDto();
        campaignRuleGroupDto.setCampaignId(campaignDto.getId());
        campaignRuleGroupDto.setRuleGroupParentId(parentGroupNodeId);
        campaignRuleGroupDto.setMatchCount(matchCount);
        campaignRuleGroupDto.setHasCoupon(CollectionUtils.isEmpty(couponSettings) ? "N" : "Y");
        CampaignRuleGroupDto saveCampaignRuleGroupDto = campaignDao.saveCampaignRuleGroup(campaignRuleGroupDto);
        BigInteger ruleGroupId = saveCampaignRuleGroupDto.getId();

        parseRuleSettings(campaignDto, ruleGroupId, ruleSettings, transactionCodeList);
        parseCouponSettings(campaignDto, ruleGroupId, couponSettings);

        if (node.getChildrenGroupNodes() != null) {
            for (CampaignCreateRqBo.CampaignGroupNodeBo child : node.getChildrenGroupNodes()) {
                // 父節點帶入當前節點ID
                parseGroupNode(campaignDto, ruleGroupId, child, transactionCodeList);
            }
        }

    }

    public void parseRuleSettings(CampaignDto campaignDto, BigInteger ruleGroupId, List<CampaignCreateRqBo.CampaignRuleSettingBo> ruleSettings, List<String> transactionCodeList) {
        if (ruleGroupId == null || CollectionUtils.isEmpty(ruleSettings)) {
            return;
        }
        for (CampaignCreateRqBo.CampaignRuleSettingBo setting : ruleSettings) {
            if (setting.getIsExtraRuleSetting()) {
                CampaignRuleSettingExtraDto campaignRuleSettingExtraDto = new CampaignRuleSettingExtraDto();
                campaignRuleSettingExtraDto.setCampaignId(campaignDto.getId());
                campaignRuleSettingExtraDto.setRuleGroupId(ruleGroupId);
                campaignRuleSettingExtraDto.setFieldName(setting.getFieldName());
                campaignRuleSettingExtraDto.setRuleName(setting.getRuleName());
                campaignRuleSettingExtraDto.setRuleValue(setting.getRuleValue());
                campaignRuleSettingExtraDto.setRuleType(setting.getRuleType());
                campaignRuleSettingExtraDto.setTransactionCode(setting.getTransactionCode());
                campaignRuleSettingExtraDao.saveCampaignRuleSettingExtra(campaignRuleSettingExtraDto);
            } else {
                CampaignRuleSettingDto campaignRuleSettingDto = new CampaignRuleSettingDto();
                campaignRuleSettingDto.setCampaignId(campaignDto.getId());
                campaignRuleSettingDto.setRuleGroupId(ruleGroupId);
                campaignRuleSettingDto.setTransactionCode(setting.getTransactionCode());
                campaignRuleSettingDto.setFieldName(setting.getFieldName());
                campaignRuleSettingDto.setRuleName(setting.getRuleName());
                campaignRuleSettingDto.setRuleValue(setting.getRuleValue());
                campaignRuleSettingDto.setRuleType(setting.getRuleType());
                campaignRuleSettingDao.saveCampaignRuleSetting(campaignRuleSettingDto);
            }
            if (!transactionCodeList.contains(setting.getTransactionCode())) {
                transactionCodeList.add(setting.getTransactionCode());
            }
        }

    }

    public void parseCouponSettings(CampaignDto campaignDto, BigInteger ruleGroupId, List<CampaignCreateRqBo.CampaignCouponTemplateSettingBo> couponSettings) {
        if (ruleGroupId == null || CollectionUtils.isEmpty(couponSettings)) {
            return;
        }

        for (CampaignCreateRqBo.CampaignCouponTemplateSettingBo setting : couponSettings) {
            CampaignRuleGroupCouponDto campaignRuleGroupCouponDto = new CampaignRuleGroupCouponDto();
            campaignRuleGroupCouponDto.setCampaignId(campaignDto.getId());
            campaignRuleGroupCouponDto.setRuleGroupId(ruleGroupId);
            campaignRuleGroupCouponDto.setCouponTemplateNo(setting.getCouponTemplateNo());
            campaignRuleGroupCouponDao.saveCampaignRuleGroupCoupon(campaignRuleGroupCouponDto);
        }

    }
}
