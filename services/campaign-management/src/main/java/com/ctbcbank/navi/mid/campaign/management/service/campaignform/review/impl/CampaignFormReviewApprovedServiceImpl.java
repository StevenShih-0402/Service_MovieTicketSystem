package com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.impl;

import com.ctbcbank.navi.mid.campaign.management.dao.*;
import com.ctbcbank.navi.mid.campaign.management.dto.*;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.CampaignFormHistoryDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignformhistory.converter.CampaignFormHistoryDtoConverter;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignFormTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.RuleNameEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.RuleTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.CampaignFormGroupNodeDataBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.CampaignFormParticipantListService;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.CampaignFormReviewRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.review.CampaignFormReviewService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignFormReviewApprovedServiceImpl implements CampaignFormReviewService {
    private final String CLASS_NAME = CampaignFormReviewApprovedServiceImpl.class.getSimpleName();
    private final CampaignFormDao campaignFormDao;
    private final CampaignDao campaignDao;
    private final CampaignFormCouponTemplateFormDao campaignFormCouponTemplateFormDao;
    private final CampaignCouponTemplateDao campaignCouponTemplateDao;
    private final CampaignRuleGroupDao campaignRuleGroupDao;
    private final CampaignRuleSettingDao campaignRuleSettingDao;
    private final CampaignRuleGroupCouponDao campaignRuleGroupCouponDao;
    private final CampaignRuleTransactionCodeDao campaignRuleTransactionCodeDao;
    private final CampaignRuleSettingExtraDao campaignRuleSettingExtraDao;
    private final CampaignFormHistoryDao campaignFormHistoryDao;
    private final ObjectMapper objectMapper;
    private final CampaignFormParticipantListService campaignFormParticipantListService;
    private final PlatformTransactionManager platformTransactionManager;

    @Override
    public void review(CampaignFormReviewRqBo campaignFormReviewRqBo) {
        // 確保事務審核完成
        approvedForm(campaignFormReviewRqBo);

        // 異步執行參與名單處理
        campaignFormParticipantListService.asyncProcessParticipantList(campaignFormReviewRqBo);

    }

    private void approvedForm(CampaignFormReviewRqBo campaignFormReviewRqBo) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);

        try {
            String campaignFormNo = campaignFormReviewRqBo.getCampaignFormNo();
            ReviewStatusEnum reviewStatusEnum = campaignFormReviewRqBo.getReviewStatus();
            QueryCampaignFormConditionDto queryCampaignFormConditionDto = new QueryCampaignFormConditionDto();
            queryCampaignFormConditionDto.setCampaignFormNo(campaignFormNo);
            List<CampaignFormDto> campaignFormDtoList = campaignFormDao.queryCampaignForm(queryCampaignFormConditionDto);
            if (CollectionUtils.isEmpty(campaignFormDtoList)) {
                throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "CampaignFormNo: " + campaignFormNo);
            }
            if (campaignFormDtoList.size() > 1) {
                throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "CampaignFormNo: " + campaignFormNo);
            }
            CampaignFormDto campaignFormDto = campaignFormDtoList.get(0);
            String campaignNo = campaignFormDto.getCampaignNo();
            CampaignFormTypeEnum campaignFormTypeEnum = campaignFormDto.getCampaignFormType();
            CampaignDto saveCampaignDto = null;
            switch (campaignFormTypeEnum) {
                case CREATE -> {
                    CampaignDto campaignDto = getCreateCampaignDto(campaignFormDto);
                    saveCampaignDto = campaignDao.saveCampaign(campaignDto);
                    log.info("[{}][update][Insert saveCampaignDto: {}]", CLASS_NAME, saveCampaignDto);
                }
                case UPDATE -> {
                    QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
                    queryCampaignConditionDto.setCampaignNo(campaignNo);
                    List<CampaignDto> campaignDtoList = campaignDao.queryCampaign(queryCampaignConditionDto);
                    if (CollectionUtils.isEmpty(campaignDtoList)) {
                        throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "CampaignFormNo: " + campaignFormNo);
                    }
                    if (campaignFormDtoList.size() > 1) {
                        throw new NaviException(FabricResponseCode.DATA_DUPLICATE, "CampaignFormNo: " + campaignFormNo);
                    }
                    CampaignDto campaignDto = campaignDtoList.get(0);

                    log.info("[{}][update][Update campaignDto Before: {}]", CLASS_NAME, campaignDto);
                    getUpdateCampaignDto(campaignDto, campaignFormDto);
                    log.info("[{}][update][Update campaignDto After: {}]", CLASS_NAME, campaignDto);
                    saveCampaignDto = campaignDao.saveCampaign(campaignDto);
                }
            }

            processCampaignCouponTemplate(campaignFormNo);

            processGroupNodeData(saveCampaignDto, campaignFormDto.getGroupNodeData());

            // 更新活動表單狀態
            campaignFormDto.setReviewStatus(reviewStatusEnum);
            CampaignFormDto saveCampaignFormDto = campaignFormDao.saveCampaignForm(campaignFormDto);

            // 新增紀錄
            CampaignFormHistoryDto campaignFormHistoryDto = CampaignFormHistoryDtoConverter.parse(saveCampaignFormDto, campaignFormReviewRqBo.getUpdateEmployeeNo());
            campaignFormHistoryDao.saveCampaignFormHistory(campaignFormHistoryDto);

            platformTransactionManager.commit(transactionStatus);
        } catch (Exception ex) {
            log.info("[{}][approvedForm][Exception: {}]", CLASS_NAME, ex);
            platformTransactionManager.rollback(transactionStatus);
            throw ex;
        }

    }

    private CampaignDto getCreateCampaignDto(CampaignFormDto campaignFormDto) {
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setCampaignNo(campaignFormDto.getCampaignNo());
        campaignDto.setName(campaignFormDto.getName());
        campaignDto.setDescription(campaignFormDto.getDescription());
        campaignDto.setStartDateTime(campaignFormDto.getStartDateTime());
        campaignDto.setEndDateTime(campaignFormDto.getEndDateTime());
        campaignDto.setCategory(campaignFormDto.getCategory());
        campaignDto.setIsImmediate(campaignFormDto.getIsImmediate());
        campaignDto.setImmediateTransactionCode(campaignFormDto.getImmediateTransactionCode());
        campaignDto.setIsListing(campaignFormDto.getIsListing());
        campaignDto.setCreateEmployeeNo(campaignFormDto.getCreateEmployeeNo());
        campaignDto.setParticipantType(campaignFormDto.getParticipantType());
        campaignDto.setParticipantListLimit(campaignFormDto.getParticipantListLimit());
        return campaignDto;
    }

    private void getUpdateCampaignDto(CampaignDto campaignDto, CampaignFormDto campaignFormDto) {
        campaignDto.setName(campaignFormDto.getName());
        campaignDto.setDescription(campaignFormDto.getDescription());
        campaignDto.setStartDateTime(campaignFormDto.getStartDateTime());
        campaignDto.setEndDateTime(campaignFormDto.getEndDateTime());
        campaignDto.setCategory(campaignFormDto.getCategory());
        campaignDto.setIsImmediate(campaignFormDto.getIsImmediate());
        campaignDto.setImmediateTransactionCode(campaignFormDto.getImmediateTransactionCode());
        campaignDto.setIsListing(campaignFormDto.getIsListing());
        campaignDto.setParticipantType(campaignFormDto.getParticipantType());
        campaignDto.setParticipantListLimit(campaignFormDto.getParticipantListLimit());
    }

    /**
     * 處理活動-優惠券樣板對應表
     *
     * @param campaignFormNo
     */
    private void processCampaignCouponTemplate(String campaignFormNo) {
        // 取得活動表單-優惠券樣板表單清單
        QueryCampaignFormCouponTemplateFormConditionDto queryCampaignFormCouponTemplateFormConditionDto = new QueryCampaignFormCouponTemplateFormConditionDto();
        queryCampaignFormCouponTemplateFormConditionDto.setCampaignFormNo(campaignFormNo);
        List<CampaignFormCouponTemplateFormDto> campaignFormCouponTemplateFormDtoList = campaignFormCouponTemplateFormDao.queryCampaignFormCouponTemplateForm(
                queryCampaignFormCouponTemplateFormConditionDto);
        campaignFormCouponTemplateFormDtoList.forEach(x -> {
            // 檢查是否存在活動-優惠券樣板
            QueryCampaignCouponTemplateConditionDto queryCampaignCouponTemplateConditionDto = new QueryCampaignCouponTemplateConditionDto();
            queryCampaignCouponTemplateConditionDto.setCampaignNo(x.getCampaignNo());
            queryCampaignCouponTemplateConditionDto.setCouponTemplateNo(x.getCouponTemplateNo());
            List<CampaignCouponTemplateDto> campaignCouponTemplateDtoList = campaignCouponTemplateDao.queryCampaignCouponTemplate(queryCampaignCouponTemplateConditionDto);
            // 不存在則新增
            if (CollectionUtils.isEmpty(campaignCouponTemplateDtoList)) {
                CampaignCouponTemplateDto campaignCouponTemplateDto = new CampaignCouponTemplateDto();
                campaignCouponTemplateDto.setCampaignNo(x.getCampaignNo());
                campaignCouponTemplateDto.setCouponTemplateNo(x.getCouponTemplateNo());
                CampaignCouponTemplateDto saveCampaignCouponTemplateDto = campaignCouponTemplateDao.saveCampaignCouponTemplate(campaignCouponTemplateDto);
                log.info("[{}][processCampaignCouponTemplate][Insert: {}]", CLASS_NAME, saveCampaignCouponTemplateDto);
            }
        });
    }

    @Transactional
    private void processGroupNodeData(CampaignDto saveCampaignDto, String groupNodeData) {
        BigInteger campaignId = saveCampaignDto.getId();
        deleteCampaignGroupNodeByCampaignId(campaignId);
        List<CampaignFormGroupNodeDataBo.GroupNodeBo> groupNodes = getGroupNodeBoList(groupNodeData);
        if (CollectionUtils.isEmpty(groupNodes)) {
            log.info("[{}][processGroupNodeData][groupNodes is empty: {}]", CLASS_NAME);
            return;
        }
        List<String> transactionCodeList = new ArrayList<>();
        // 第一層的父節點為NULL
        parseGroupNode(saveCampaignDto, null, groupNodes.get(0), transactionCodeList);
        log.info("[{}][update][transactionCodeList: {}]", CLASS_NAME, transactionCodeList);
        transactionCodeList.forEach(x -> {
            CampaignRuleTransactionCodeDto campaignRuleTransactionCodeDto = new CampaignRuleTransactionCodeDto();
            campaignRuleTransactionCodeDto.setCampaignId(campaignId);
            campaignRuleTransactionCodeDto.setTransactionCode(x);
            campaignRuleTransactionCodeDao.saveCampaignRuleTransactionCode(campaignRuleTransactionCodeDto);
        });
    }

    private List<CampaignFormGroupNodeDataBo.GroupNodeBo> getGroupNodeBoList(String groupNodeData) {
        List<CampaignFormGroupNodeDataBo.GroupNodeBo> groupNodeBoList = new ArrayList<>();
        if (StringUtils.isBlank(groupNodeData)) {
            return groupNodeBoList;
        }
        try {
            groupNodeBoList = objectMapper.readValue(groupNodeData, new TypeReference<List<CampaignFormGroupNodeDataBo.GroupNodeBo>>() {
            });
        } catch (Exception ex) {
            log.error("[{}][getGroupNodeBoList][readValue error: {}]", CLASS_NAME, ex);
        }
        return groupNodeBoList;
    }

    /**
     * 刪除舊節點資料 By CampaignID
     *
     * @param campaignId
     */
    @Transactional
    private void deleteCampaignGroupNodeByCampaignId(BigInteger campaignId) {
        // 刪除RuleGroup By CampaignID
        int ruleGroupDelete = campaignRuleGroupDao.deleteByCampaignId(campaignId);
        log.info("[{}][deleteCampaignGroupNodeByCampaignId][ruleGroupDelete: {}]", CLASS_NAME, ruleGroupDelete);
        // 刪除RuleGroupCoupon By CampaignID
        int ruleGroupCouponDelete = campaignRuleGroupCouponDao.deleteByCampaignId(campaignId);
        log.info("[{}][deleteCampaignGroupNodeByCampaignId][ruleGroupCouponDelete: {}]", CLASS_NAME, ruleGroupCouponDelete);
        // 刪除RuleSetting By CampaignID
        int ruleSettingDelete = campaignRuleSettingDao.deleteByCampaignId(campaignId);
        log.info("[{}][deleteCampaignGroupNodeByCampaignId][ruleSettingDelete: {}]", CLASS_NAME, ruleSettingDelete);
        // 刪除RuleSettingExtra By CampaignID
        int ruleSettingExtraDelete = campaignRuleSettingExtraDao.deleteByCampaignId(campaignId);
        log.info("[{}][deleteCampaignGroupNodeByCampaignId][ruleSettingExtraDelete: {}]", CLASS_NAME, ruleSettingExtraDelete);
        // 刪除RuleTransactionCode By CampaignID
        int ruleTransactionCodeDelete = campaignRuleTransactionCodeDao.deleteByCampaignId(campaignId);
        log.info("[{}][deleteCampaignGroupNodeByCampaignId][ruleTransactionCodeDelete: {}]", CLASS_NAME, ruleTransactionCodeDelete);
    }

    public void parseGroupNode(CampaignDto campaignDto, BigInteger parentGroupNodeId, CampaignFormGroupNodeDataBo.GroupNodeBo node, List<String> transactionCodeList) {
        if (ObjectUtils.isEmpty(node)) {
            return;
        }
        // 取節點的規則設定、優惠券設定、符合條件數量
        CampaignFormGroupNodeDataBo.GroupNodeDataBo groupNodeDataBo = node.getData();
        List<CampaignFormGroupNodeDataBo.RuleSettingBo> ruleSettings = groupNodeDataBo.getRuleSettings();
        List<CampaignFormGroupNodeDataBo.CouponTemplateSettingBo> couponSettings = groupNodeDataBo.getCouponTemplateSettings();
        long matchCount = groupNodeDataBo.getMatchCount().longValue();

        CampaignRuleGroupDto campaignRuleGroupDto = new CampaignRuleGroupDto();
        campaignRuleGroupDto.setCampaignId(campaignDto.getId());
        campaignRuleGroupDto.setRuleGroupParentId(parentGroupNodeId);
        campaignRuleGroupDto.setMatchCount(matchCount);
        campaignRuleGroupDto.setHasCoupon(CollectionUtils.isEmpty(couponSettings) ? "N" : "Y");
        CampaignRuleGroupDto saveCampaignRuleGroupDto = campaignDao.saveCampaignRuleGroup(campaignRuleGroupDto);
        BigInteger ruleGroupId = saveCampaignRuleGroupDto.getId();

        parseRuleSettings(campaignDto, ruleGroupId, ruleSettings, transactionCodeList);
        parseCouponSettings(campaignDto, ruleGroupId, couponSettings);

        if (!CollectionUtils.isEmpty(node.getChildren())) {
            for (CampaignFormGroupNodeDataBo.GroupNodeBo child : node.getChildren()) {
                // 父節點帶入當前節點ID
                parseGroupNode(campaignDto, ruleGroupId, child, transactionCodeList);
            }
        }
    }

    public void parseRuleSettings(CampaignDto campaignDto, BigInteger ruleGroupId, List<CampaignFormGroupNodeDataBo.RuleSettingBo> ruleSettings, List<String> transactionCodeList) {
        if (ruleGroupId == null || CollectionUtils.isEmpty(ruleSettings)) {
            return;
        }
        for (CampaignFormGroupNodeDataBo.RuleSettingBo setting : ruleSettings) {
            boolean isExtraRuleSetting = StringUtils.equalsAnyIgnoreCase(setting.getRuleName(), RuleNameEnum.IS_TRUE.name()) && StringUtils.equalsAnyIgnoreCase(
                    setting.getRuleType(), RuleTypeEnum.EQUAL.name());

            if (isExtraRuleSetting) {
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

    public void parseCouponSettings(CampaignDto campaignDto, BigInteger ruleGroupId, List<CampaignFormGroupNodeDataBo.CouponTemplateSettingBo> couponSettings) {
        if (ruleGroupId == null || CollectionUtils.isEmpty(couponSettings)) {
            return;
        }

        for (CampaignFormGroupNodeDataBo.CouponTemplateSettingBo setting : couponSettings) {
            CampaignRuleGroupCouponDto campaignRuleGroupCouponDto = new CampaignRuleGroupCouponDto();
            campaignRuleGroupCouponDto.setCampaignId(campaignDto.getId());
            campaignRuleGroupCouponDto.setRuleGroupId(ruleGroupId);
            campaignRuleGroupCouponDto.setCouponTemplateNo(setting.getCouponTemplateNo());
            campaignRuleGroupCouponDao.saveCampaignRuleGroupCoupon(campaignRuleGroupCouponDto);
        }
    }
}
