package com.ctbcbank.navi.mid.campaign.management.service.campaign.update;

import com.ctbcbank.navi.mid.campaign.management.dao.*;
import com.ctbcbank.navi.mid.campaign.management.dto.*;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
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
public class CampaignUpdateServiceImpl implements CampaignUpdateService {
    private final String CLASS_NAME = CampaignUpdateServiceImpl.class.getSimpleName();
    private final CampaignDao campaignDao;
    private final CampaignRuleGroupDao campaignRuleGroupDao;
    private final CampaignRuleSettingDao campaignRuleSettingDao;
    private final CampaignRuleGroupCouponDao campaignRuleGroupCouponDao;
    private final CampaignRuleTransactionCodeDao campaignRuleTransactionCodeDao;
    private final CampaignRuleSettingExtraDao campaignRuleSettingExtraDao;

    @Override
    @Transactional
    public CampaignUpdateRsBo update(CampaignUpdateRqBo campaignUpdateRqBo) {
        CampaignUpdateRsBo campaignUpdateRsBo = new CampaignUpdateRsBo();
        BigInteger campaignId = campaignUpdateRqBo.getCampaignInfoBo().getId();
        QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
        queryCampaignConditionDto.setId(campaignId);
        List<CampaignDto> queryDtoList = campaignDao.queryCampaign(queryCampaignConditionDto);
        log.info("[{}][update][queryDtoList: {}]", CLASS_NAME, queryDtoList);
        if (CollectionUtils.isEmpty(queryDtoList) || queryDtoList.size() > 1) {
            throw new NaviException(FabricResponseCode.INVALID_DATA, "CampaignId: " + campaignId + ", Data Not Found || size > 1");
        }
        CampaignDto campaignDto = queryDtoList.get(0);

        if (campaignUpdateRqBo.isUpdateCampaignInfo()) {
            BeanUtils.copyProperties(campaignUpdateRqBo.getCampaignInfoBo(), campaignDto, "id", "status");
            campaignDto = campaignDao.saveCampaign(campaignDto);
        }

        if (campaignUpdateRqBo.isUpdateCampaignGroupNode()) {
            // 刪除舊節點資料 By CampaignID
            deleteCampaignGroupNodeByCampaignId(campaignId);
            if (ObjectUtils.isNotEmpty(campaignUpdateRqBo.getGroupNodes()) && !CollectionUtils.isEmpty(campaignUpdateRqBo.getGroupNodes())) {

                List<String> transactionCodeList = new ArrayList<>();
                // 第一層的父節點為NULL
                parseGroupNode(campaignDto, null, campaignUpdateRqBo.getGroupNodes().get(0), transactionCodeList);
                log.info("[{}][update][transactionCodeList: {}]", CLASS_NAME, transactionCodeList);
                transactionCodeList.forEach(x -> {
                    CampaignRuleTransactionCodeDto campaignRuleTransactionCodeDto = new CampaignRuleTransactionCodeDto();
                    campaignRuleTransactionCodeDto.setCampaignId(campaignId);
                    campaignRuleTransactionCodeDto.setTransactionCode(x);
                    campaignRuleTransactionCodeDao.saveCampaignRuleTransactionCode(campaignRuleTransactionCodeDto);
                });
            }
        }
        campaignUpdateRsBo.setCampaignId(campaignId);
        return campaignUpdateRsBo;
    }

    /**
     * 刪除舊節點資料 By CampaignID
     *
     * @param campaignId
     */
    private void deleteCampaignGroupNodeByCampaignId(BigInteger campaignId) {
        // 如果資料庫沒有刪除，經過DELETE後回傳的參數也會是0，不用特別判斷是否為0丟Exception
        // 刪除RuleGroup By CampaignID
        int ruleGroupDelete = campaignRuleGroupDao.deleteByCampaignId(campaignId);
        log.info("[{}][deleteCampaignGroupNodeByCampaignId][ruleGroupDelete: {}]", CLASS_NAME, ruleGroupDelete);
//        if (ruleGroupDelete == 0) {
//            throw new NaviException(ApiResponseCode.ILLEGAL_PERSISTENT_DATA, "Delete RuleGroup Failed.");
//        }
        // 刪除RuleGroupCoupon By CampaignID
        int ruleGroupCouponDelete = campaignRuleGroupCouponDao.deleteByCampaignId(campaignId);
        log.info("[{}][deleteCampaignGroupNodeByCampaignId][ruleGroupCouponDelete: {}]", CLASS_NAME, ruleGroupCouponDelete);
//        if (ruleGroupCouponDelete == 0) {
//            throw new NaviException(ApiResponseCode.ILLEGAL_PERSISTENT_DATA, "Delete RuleGroupCoupon Failed.");
//        }
        // 刪除RuleSetting By CampaignID
        int ruleSettingDelete = campaignRuleSettingDao.deleteByCampaignId(campaignId);
        log.info("[{}][deleteCampaignGroupNodeByCampaignId][ruleSettingDelete: {}]", CLASS_NAME, ruleSettingDelete);
//        if (ruleSettingDelete == 0) {
//            throw new NaviException(ApiResponseCode.ILLEGAL_PERSISTENT_DATA, "Delete RuleSetting Failed.");
//        }
        // 刪除RuleSettingExtra By CampaignID
        int ruleSettingExtraDelete = campaignRuleSettingExtraDao.deleteByCampaignId(campaignId);
        log.info("[{}][deleteCampaignGroupNodeByCampaignId][ruleSettingExtraDelete: {}]", CLASS_NAME, ruleSettingExtraDelete);
//        if (ruleSettingExtraDelete == 0) {
//            throw new NaviException(ApiResponseCode.ILLEGAL_PERSISTENT_DATA, "Delete RuleSettingExtra Failed.");
//        }
        // 刪除RuleTransactionCode By CampaignID
        int ruleTransactionCodeDelete = campaignRuleTransactionCodeDao.deleteByCampaignId(campaignId);
        log.info("[{}][deleteCampaignGroupNodeByCampaignId][ruleTransactionCodeDelete: {}]", CLASS_NAME, ruleTransactionCodeDelete);
//        if (ruleTransactionCodeDelete == 0) {
//            throw new NaviException(ApiResponseCode.ILLEGAL_PERSISTENT_DATA, "Delete RuleTransactionCode Failed.");
//        }
    }

    public void parseGroupNode(CampaignDto campaignDto, BigInteger parentGroupNodeId, CampaignUpdateRqBo.CampaignGroupNodeBo node, List<String> transactionCodeList) {
        if (node == null) {
            return;
        }
        // 取節點的規則設定、優惠券設定、符合條件數量
        CampaignUpdateRqBo.CampaignGroupNodeDataBo campaignGroupNodeDataBo = node.getGroupNodeData();
        List<CampaignUpdateRqBo.CampaignRuleSettingBo> ruleSettings = campaignGroupNodeDataBo.getRuleSettings();
        List<CampaignUpdateRqBo.CampaignCouponTemplateSettingBo> couponSettings = campaignGroupNodeDataBo.getCouponTemplateSettings();
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
            for (CampaignUpdateRqBo.CampaignGroupNodeBo child : node.getChildrenGroupNodes()) {
                // 父節點帶入當前節點ID
                parseGroupNode(campaignDto, ruleGroupId, child, transactionCodeList);
            }
        }

    }

    public void parseRuleSettings(CampaignDto campaignDto, BigInteger ruleGroupId, List<CampaignUpdateRqBo.CampaignRuleSettingBo> ruleSettings, List<String> transactionCodeList) {
        if (ruleGroupId == null || CollectionUtils.isEmpty(ruleSettings)) {
            return;
        }
        for (CampaignUpdateRqBo.CampaignRuleSettingBo setting : ruleSettings) {
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

    public void parseCouponSettings(CampaignDto campaignDto, BigInteger ruleGroupId, List<CampaignUpdateRqBo.CampaignCouponTemplateSettingBo> couponSettings) {
        if (ruleGroupId == null || CollectionUtils.isEmpty(couponSettings)) {
            return;
        }

        for (CampaignUpdateRqBo.CampaignCouponTemplateSettingBo setting : couponSettings) {
            CampaignRuleGroupCouponDto campaignRuleGroupCouponDto = new CampaignRuleGroupCouponDto();
            campaignRuleGroupCouponDto.setCampaignId(campaignDto.getId());
            campaignRuleGroupCouponDto.setRuleGroupId(ruleGroupId);
            campaignRuleGroupCouponDto.setCouponTemplateNo(setting.getCouponTemplateNo());
            campaignRuleGroupCouponDao.saveCampaignRuleGroupCoupon(campaignRuleGroupCouponDto);
        }
    }


}
