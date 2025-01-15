package com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querynonsynchronous;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignRuleTransactionCodeDao;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignDto;
import com.ctbcbank.navi.mid.campaign.management.dto.CampaignRuleTransactionCodeDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.QueryCampaignRuleTransactionCodeConditionDto;
import com.ctbcbank.navi.mid.campaign.management.utils.StreamUtils;
import com.ctbcbank.navi.mid.campaign.management.vo.RuleEngine;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class QueryRuleEngineNonSynchronousServiceImpl implements QueryRuleEngineNonSynchronousService {
    private final String CLASS_NAME = QueryRuleEngineNonSynchronousServiceImpl.class.getSimpleName();
    private final CampaignDao campaignDao;
    private final CampaignRuleTransactionCodeDao campaignRuleTransactionCodeDao;

    @Override
    public QueryRuleEngineNonSynchronousRsBo query(QueryRuleEngineNonSynchronousRqBo queryRuleEngineNonSynchronousRqBo) {
        QueryRuleEngineBasicInformationBo queryRuleEngineBasicInformationBo = queryRuleEngineNonSynchronousRqBo.getQueryRuleEngineBasicInformationBo();
        List<BigInteger> campaignIdList = getCampaignIdList(queryRuleEngineBasicInformationBo);

        if (ObjectUtils.isNotEmpty(queryRuleEngineBasicInformationBo.getTransactionCode())) {
            campaignIdList = getCampaignIdListByTransactionCode(queryRuleEngineBasicInformationBo.getTransactionCode(), campaignIdList);

        }

        QueryRuleEngineNonSynchronousRsBo queryRuleEngineNonSynchronousRsBo = new QueryRuleEngineNonSynchronousRsBo();
        Map<BigInteger, RuleEngine> ruleEngineTree = new LinkedHashMap<>();
        campaignIdList.forEach(x -> {
            RuleEngine ruleEngine = campaignDao.getRuleEngineByCampaignId(x);
            log.info("[{}][queryRuleEngineTreeByCondition][{} ruleEngine: {}]", CLASS_NAME, x, ruleEngine);
            if (ruleEngine != null) {
                ruleEngineTree.put(x, ruleEngine);
            }
        });
        queryRuleEngineNonSynchronousRsBo.setRuleEngineTree(ruleEngineTree);
        return queryRuleEngineNonSynchronousRsBo;
    }

    /**
     * 取得Campaign ID清單(查Campaign)，By 狀態為上架 且 轉帳時間 且 非即時
     *
     * @param queryRuleEngineBasicInformationBo
     * @return
     */
    private List<BigInteger> getCampaignIdList(QueryRuleEngineBasicInformationBo queryRuleEngineBasicInformationBo) {
        QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
        queryCampaignConditionDto.setCampaignDateTime(queryRuleEngineBasicInformationBo.getTranDateTime());
        queryCampaignConditionDto.setIsListing(true);
        queryCampaignConditionDto.setIsImmediate(false);

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
}
