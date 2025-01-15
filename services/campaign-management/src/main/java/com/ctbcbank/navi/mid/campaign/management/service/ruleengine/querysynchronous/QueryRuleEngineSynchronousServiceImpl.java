package com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querysynchronous;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignRuleSettingExtraDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignRuleTransactionCodeDao;
import com.ctbcbank.navi.mid.campaign.management.dto.*;
import com.ctbcbank.navi.mid.campaign.management.utils.StreamUtils;
import com.ctbcbank.navi.mid.campaign.management.vo.RuleEngine;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class QueryRuleEngineSynchronousServiceImpl implements QueryRuleEngineSynchronousService {
    private final String CLASS_NAME = QueryRuleEngineSynchronousServiceImpl.class.getSimpleName();
    private final CampaignDao campaignDao;
    private final CampaignRuleTransactionCodeDao campaignRuleTransactionCodeDao;
    private final CampaignRuleSettingExtraDao campaignRuleSettingExtraDao;

    @Override
    public QueryRuleEngineSynchronousRsBo query(QueryRuleEngineSynchronousRqBo queryRuleEngineSynchronousRqBo) {
        QueryRuleEngineBasicInformationBo queryRuleEngineBasicInformationBo = queryRuleEngineSynchronousRqBo.getQueryRuleEngineBasicInformationBo();

        List<BigInteger> campaignIdList = getCampaignIdList(queryRuleEngineBasicInformationBo);

        if (ObjectUtils.isNotEmpty(queryRuleEngineBasicInformationBo.getTransactionCode())) {
            campaignIdList = getCampaignIdListByTransactionCode(queryRuleEngineBasicInformationBo.getTransactionCode(), campaignIdList);
            campaignIdList = getCampaignIdListByExtraRule(campaignIdList, queryRuleEngineBasicInformationBo);
        }

        QueryRuleEngineSynchronousRsBo queryRuleEngineSynchronousRsBo = new QueryRuleEngineSynchronousRsBo();
        Map<BigInteger, RuleEngine> ruleEngineTree = new LinkedHashMap<>();
        campaignIdList.forEach(x -> {
            RuleEngine ruleEngine = campaignDao.getRuleEngineByCampaignId(x);
            log.info("[{}][queryRuleEngineTreeByCondition][{} ruleEngine: {}]", CLASS_NAME, x, ruleEngine);
            if (ruleEngine != null) {
                ruleEngineTree.put(x, ruleEngine);
            }
        });
        queryRuleEngineSynchronousRsBo.setRuleEngineTree(ruleEngineTree);
        return queryRuleEngineSynchronousRsBo;
    }

    /**
     * 取得Campaign ID清單(查Campaign)，By 狀態為上架 且 轉帳時間
     *
     * @param queryRuleEngineBasicInformationBo
     * @return
     */
    private List<BigInteger> getCampaignIdList(QueryRuleEngineBasicInformationBo queryRuleEngineBasicInformationBo) {
        QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
        queryCampaignConditionDto.setCampaignDateTime(queryRuleEngineBasicInformationBo.getTranDateTime());
        queryCampaignConditionDto.setIsListing(true);
        queryCampaignConditionDto.setIsImmediate(true);

        List<BigInteger> campaignIdList = new ArrayList<>();
        List<CampaignDto> campaignDtoList = campaignDao.queryCampaign(queryCampaignConditionDto);
        campaignIdList = campaignDtoList.stream().map(CampaignDto::getId).toList();
        log.info("[{}][getCampaignIdList][campaignDtoList Size: {}]", CLASS_NAME, campaignDtoList.size());
        log.info("[{}][getCampaignIdList][campaignIdList : {}]", CLASS_NAME, campaignIdList);
        if (CollectionUtils.isEmpty(campaignDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND);
        }
        return campaignIdList;
    }

    /**
     * 取得Campaign ID清單(查CampaignRuleTransactionCode)，By TransactionCode
     *
     * @param transactionCode
     * @param campaignIdList
     * @return
     */
    private List<BigInteger> getCampaignIdListByTransactionCode(String transactionCode, List<BigInteger> campaignIdList) {
        List<BigInteger> reCampaignIdList = new ArrayList<>();
        List<String> transactionCodeList = Collections.singletonList(transactionCode);
        QueryCampaignRuleTransactionCodeConditionDto campaignRuleTransactionCodeConditionDto = new QueryCampaignRuleTransactionCodeConditionDto();
        campaignRuleTransactionCodeConditionDto.setCampaignIdList(campaignIdList);
        campaignRuleTransactionCodeConditionDto.setTransactionCodeList(transactionCodeList);
        List<CampaignRuleTransactionCodeDto> campaignRuleTransactionCodeDtoList = campaignRuleTransactionCodeDao.queryCampaignRuleTransactionCode(campaignRuleTransactionCodeConditionDto);
        reCampaignIdList = campaignRuleTransactionCodeDtoList.stream().filter(StreamUtils.distinctByKeys(CampaignRuleTransactionCodeDto::getCampaignId))
                .map(CampaignRuleTransactionCodeDto::getCampaignId).toList();
        log.info("[{}][getCampaignIdListByTransactionCode][campaignRuleTransactionCodeDtoList Size: {}]", CLASS_NAME, campaignRuleTransactionCodeDtoList.size());
        log.info("[{}][getCampaignIdListByTransactionCode][reCampaignIdList : {}]", CLASS_NAME, reCampaignIdList);
        if (CollectionUtils.isEmpty(campaignRuleTransactionCodeDtoList)) {
            new NaviException(FabricResponseCode.DATA_NOT_FOUND);
        }
        return reCampaignIdList;
    }

    private List<BigInteger> getCampaignIdListByExtraRule(List<BigInteger> campaignIdList, QueryRuleEngineBasicInformationBo queryRuleEngineBasicInformationBo) {
        if (ObjectUtils.isEmpty(queryRuleEngineBasicInformationBo.getDynamicFields())) {
            return campaignIdList;
        }
        Map<String, String> dynamicFields = queryRuleEngineBasicInformationBo.getDynamicFields();

        String transactionCode = queryRuleEngineBasicInformationBo.getTransactionCode();
        QueryCampaignRuleSettingExtraConditionDto queryCampaignRuleSettingExtraConditionDto = new QueryCampaignRuleSettingExtraConditionDto();
        queryCampaignRuleSettingExtraConditionDto.setTransactionCode(transactionCode);
        queryCampaignRuleSettingExtraConditionDto.setCampaignIdList(campaignIdList);
        List<BigInteger> reCampaignIdList = new ArrayList<>();
        List<CampaignRuleSettingExtraDto> campaignRuleSettingExtraDtoList = campaignRuleSettingExtraDao.queryCampaignRuleSettingExtra(queryCampaignRuleSettingExtraConditionDto);
        campaignIdList.forEach(campaignId -> {
            List<CampaignRuleSettingExtraDto> filterCampaignRuleSettingExtraDtoList = campaignRuleSettingExtraDtoList.stream().filter(x -> x.getCampaignId().equals(campaignId)).toList();
            // 預設為符合
            boolean isCheck = true;
            for (CampaignRuleSettingExtraDto dto : filterCampaignRuleSettingExtraDtoList) {
                String fieldName = dto.getFieldName();
                // 有值比較是否一致
                if (dynamicFields.containsKey(fieldName)) {
                    String fieldValue = dynamicFields.get(fieldName);
                    // 不一致設為不符合
                    if (!StringUtils.equals(fieldValue, dto.getRuleValue())) {
                        isCheck = false;
                    }
                }
                // 沒有值設為不符合
                else {
                    isCheck = false;
                }
            }
            if (isCheck) {
                reCampaignIdList.add(campaignId);
            }
        });
        return reCampaignIdList;
    }
}
