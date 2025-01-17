package com.ctbcbank.navi.mid.campaign.management.service.campaign;

import com.ctbcbank.navi.mid.campaign.management.dao.*;
import com.ctbcbank.navi.mid.campaign.management.dto.*;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignparticipantlist.CampaignParticipantListDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaignparticipantlist.QueryCampaignParticipantListConditionDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.ExchangeExtraRuleEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.TransactionCodeEnum;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignDao campaignDao;
    private final CampaignRuleTransactionCodeDao campaignRuleTransactionCodeDao;
    private final CampaignRuleDao campaignRuleDao;
    private final CampaignRuleExtraDao campaignRuleExtraDao;
    private final CampaignRuleSettingExtraDao campaignRuleSettingExtraDao;
    private final CampaignParticipantListDao campaignParticipantListDao;
    private final String CLASS_NAME = CampaignServiceImpl.class.getSimpleName();

    @Override
    public QueryRuleEngineTreeByConditionResponseBo queryRuleEngineTreeByCondition(QueryRuleEngineTreeByConditionRequestBo queryRuleEngineTreeByConditionRequestBo) {
        QueryRuleEngineTreeByConditionResponseBo queryRuleEngineTreeByConditionResponseBo = new QueryRuleEngineTreeByConditionResponseBo();

        List<CampaignDto> campaignDtoList = getCampaignIdList(queryRuleEngineTreeByConditionRequestBo);

        log.info("[{}][campaignDtoList][campaignDtoList size : {}]", CLASS_NAME, campaignDtoList.size());

        // 需要名單的活動清單
        List<CampaignDto> isParticipantListCampaignDtoList = campaignDtoList.stream()
                .filter(x -> x.getParticipantType().compareTo(CampaignParticipantTypeEnum.CUSTOMER_LIST) == 0 || x.getParticipantType().compareTo(CampaignParticipantTypeEnum.ONLINE) == 0).toList();
        List<String> isCheckParticipantListCampaignNoList = checkIsParticipantListByCampaignListAndInvolvedPartyNo(
                isParticipantListCampaignDtoList, queryRuleEngineTreeByConditionRequestBo.getInvolvedPartyNo());
        List<BigInteger> isParticipantListCampaignIdList = isParticipantListCampaignDtoList.stream().filter(x -> isCheckParticipantListCampaignNoList.contains(x.getCampaignNo()))
                .map(CampaignDto::getId).toList();
        log.info("[{}][queryRuleEngineTreeByCondition][isParticipantListCampaignIdList: {}]", CLASS_NAME, isParticipantListCampaignIdList);

        // 不需要名單的活動ID清單
        List<BigInteger> isNotParticipantListCampaignIdList = campaignDtoList.stream().filter(x -> x.getParticipantType().compareTo(CampaignParticipantTypeEnum.FREE) == 0).map(CampaignDto::getId)
                .toList();
        log.info("[{}][queryRuleEngineTreeByCondition][isNotParticipantListCampaignIdList: {}]", CLASS_NAME, isNotParticipantListCampaignIdList);

        List<BigInteger> campaignIdList = Stream.concat(isParticipantListCampaignIdList.stream(), isNotParticipantListCampaignIdList.stream()).collect(Collectors.toList());
        log.info("[{}][queryRuleEngineTreeByCondition][campaignIdList: {}]", CLASS_NAME, campaignIdList);
        if (CollectionUtils.isEmpty(campaignIdList)) {
            log.info("[{}][queryRuleEngineTreeByCondition][campaignIdList is empty.]", CLASS_NAME);
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND);
        }

        if (!ObjectUtils.isEmpty(queryRuleEngineTreeByConditionRequestBo.getTransactionCode())) {
            campaignIdList = getCampaignIdListByTransactionCode(queryRuleEngineTreeByConditionRequestBo.getTransactionCode(), campaignIdList);
            campaignIdList = getCampaignIdListByExtraRule(queryRuleEngineTreeByConditionRequestBo, campaignIdList);
        }
        log.info("[{}][queryRuleEngineTreeByCondition][campaignIdList: {}]", CLASS_NAME, campaignIdList);


        Map<BigInteger, RuleEngine> ruleEngineTree = new LinkedHashMap<>();
        campaignIdList.forEach(x -> {
            RuleEngine ruleEngine = campaignDao.getRuleEngineByCampaignId(x);
            log.info("[{}][queryRuleEngineTreeByCondition][{} ruleEngine: {}]", CLASS_NAME, x, ruleEngine);
            if (ruleEngine != null) {
                ruleEngineTree.put(x, ruleEngine);
            }
        });

        queryRuleEngineTreeByConditionResponseBo.setRuleEngineTree(ruleEngineTree);
        return queryRuleEngineTreeByConditionResponseBo;
    }

    /**
     * 取得 活動清單(查Campaign)，By 狀態為上架 且 轉帳時間
     *
     * @param queryRuleEngineTreeByConditionRequestBo
     * @return
     */
    private List<CampaignDto> getCampaignIdList(QueryRuleEngineTreeByConditionRequestBo queryRuleEngineTreeByConditionRequestBo) {
        QueryCampaignConditionDto queryCampaignConditionDto = new QueryCampaignConditionDto();
        queryCampaignConditionDto.setCampaignDateTime(queryRuleEngineTreeByConditionRequestBo.getTransactionDateTime());
        queryCampaignConditionDto.setIsListing(true);
        queryCampaignConditionDto.setIsImmediate(queryRuleEngineTreeByConditionRequestBo.isSynchronous());

        List<CampaignDto> campaignDtoList = campaignDao.queryCampaign(queryCampaignConditionDto);
        log.info("[{}][getCampaignIdList][campaignDtoList Size: {}]", CLASS_NAME, campaignDtoList.size());

        if (CollectionUtils.isEmpty(campaignDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND);
        }

        return campaignDtoList;
    }

    /**
     * 檢查有參與名單的活動中，是否該客戶有在參與名單內
     *
     * @param campaignDtoList
     * @return
     */
    private List<String> checkIsParticipantListByCampaignListAndInvolvedPartyNo(List<CampaignDto> campaignDtoList, BigInteger involvedPartyNo) {
        List<String> reCampaignNoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignDtoList) || ObjectUtils.isEmpty(involvedPartyNo)) {
            log.error("[{}][checkIsParticipantListByCampaignListAndInvolvedPartyNo][campaignDtoList or involvedPartyNo is empty.]", CLASS_NAME);
            return reCampaignNoList;
        }
        List<String> campaignNoList = campaignDtoList.stream().map(CampaignDto::getCampaignNo).toList();
        List<String> campaignParticipantListVersionList = campaignDtoList.stream().map(CampaignDto::getParticipantListVersion).toList();
        log.info("[{}][checkIsParticipantListByCampaignListAndInvolvedPartyNo][campaignNoList: {}]", CLASS_NAME, campaignNoList);
        log.info("[{}][checkIsParticipantListByCampaignListAndInvolvedPartyNo][campaignParticipantListVersionList: {}]", CLASS_NAME, campaignParticipantListVersionList);
        if (CollectionUtils.isEmpty(campaignNoList) || CollectionUtils.isEmpty(campaignParticipantListVersionList)) {
            log.error("[{}][checkIsParticipantListByCampaignListAndInvolvedPartyNo][campaignNoList or campaignParticipantListVersionList is empty.]", CLASS_NAME);
            return campaignNoList;
        }
        QueryCampaignParticipantListConditionDto queryCampaignParticipantListConditionDto = new QueryCampaignParticipantListConditionDto();
        queryCampaignParticipantListConditionDto.setCampaignNoList(campaignNoList);
        queryCampaignParticipantListConditionDto.setParticipantListVersionList(campaignParticipantListVersionList);
        queryCampaignParticipantListConditionDto.setIpNo(involvedPartyNo);
        List<CampaignParticipantListDto> campaignParticipantListDtoList = campaignParticipantListDao.queryCampaignParticipantList(queryCampaignParticipantListConditionDto);
        reCampaignNoList = campaignParticipantListDtoList.stream().map(CampaignParticipantListDto::getCampaignNo).toList();
        return reCampaignNoList;
    }

    /**
     * 取得Campaign ID清單(查CampaignRuleTransactionCode)，By TransactionCode
     *
     * @param transactionCode
     * @param campaignIdList
     * @return
     */
    private List<BigInteger> getCampaignIdListByTransactionCode(String transactionCode, List<BigInteger> campaignIdList) {
        if (CollectionUtils.isEmpty(campaignIdList)) {
            log.error("[{}][getCampaignIdListByTransactionCode][campaignIdList is empty.]", CLASS_NAME);
            new NaviException(FabricResponseCode.DATA_NOT_FOUND);
        }
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

    /**
     * 取得Campaign ID清單(查CampaignRuleSetting)，By 額外條件
     *
     * @param queryRuleEngineTreeByConditionRequestBo
     * @param campaignIdList
     * @return
     */
    private List<BigInteger> getCampaignIdListByExtraRule(QueryRuleEngineTreeByConditionRequestBo queryRuleEngineTreeByConditionRequestBo, List<BigInteger> campaignIdList) {
        if (CollectionUtils.isEmpty(campaignIdList)) {
            log.error("[{}][getCampaignIdListByTransactionCode][campaignIdList is empty.]", CLASS_NAME);
            new NaviException(FabricResponseCode.DATA_NOT_FOUND);
        }
        List<BigInteger> reCampaignIdList = new ArrayList<>();
        String transactionCode = queryRuleEngineTreeByConditionRequestBo.getTransactionCode();
        Optional<TransactionCodeEnum> transactionCodeEnumOptional = Optional.of(TransactionCodeEnum.fromCode(transactionCode));
        if (transactionCodeEnumOptional.isPresent()) {
            QueryCampaignRuleSettingExtraConditionDto queryCampaignRuleSettingExtraConditionDto = new QueryCampaignRuleSettingExtraConditionDto();
            queryCampaignRuleSettingExtraConditionDto.setTransactionCode(transactionCode);
            queryCampaignRuleSettingExtraConditionDto.setCampaignIdList(campaignIdList);
            log.info("[{}][getCampaignIdListByExtraRule][queryCampaignRuleExtraConditionDto : {}]", CLASS_NAME, queryCampaignRuleSettingExtraConditionDto);
            List<QueryCampaignRuleSettingExtraConditionDto.RuleSetting> ruleSettingList = getRuleSettingListByTransactionCode(
                    transactionCodeEnumOptional.get(), queryRuleEngineTreeByConditionRequestBo);
            log.info("[{}][getCampaignIdListByExtraRule][ruleSettingList : {}]", CLASS_NAME, ruleSettingList);

            List<CampaignRuleSettingExtraDto> campaignRuleSettingExtraDtoList = campaignRuleSettingExtraDao.queryCampaignRuleSettingExtra(queryCampaignRuleSettingExtraConditionDto);
            log.info("[{}][getCampaignIdListByExtraRule][campaignRuleSettingExtraDtoList.size : {}]", CLASS_NAME, campaignRuleSettingExtraDtoList.size());
            AtomicReference<List<BigInteger>> currentCampaignIdList = new AtomicReference<>(campaignIdList);
            ruleSettingList.forEach(ruleSetting -> {
                log.info("[{}][getCampaignIdListByExtraRule][==============================]", CLASS_NAME);
                log.info("[{}][getCampaignIdListByExtraRule][currentCampaignIdList : {}]", CLASS_NAME, currentCampaignIdList);
                log.info("[{}][getCampaignIdListByExtraRule][filter ruleName : {}, ruleValue: {}]", CLASS_NAME, ruleSetting.getRuleName(), ruleSetting.getRuleValue());
                List<CampaignRuleSettingExtraDto> filterCampaignRuleSettingExtraDtoList = campaignRuleSettingExtraDtoList.stream()
                        .filter(x -> currentCampaignIdList.get().contains(x.getCampaignId()) && StringUtils.equals(ruleSetting.getRuleName(), x.getRuleName()) && StringUtils.equals(
                                ruleSetting.getRuleValue(), x.getRuleValue())).toList();
                log.info("[{}][getCampaignIdListByExtraRule][filterCampaignRuleSettingExtraDtoList : {}]", CLASS_NAME, filterCampaignRuleSettingExtraDtoList);
                List<BigInteger> filterCampaignIdList = filterCampaignRuleSettingExtraDtoList.stream().filter(StreamUtils.distinctByKeys(CampaignRuleSettingExtraDto::getCampaignId))
                        .map(CampaignRuleSettingExtraDto::getCampaignId).toList();
                currentCampaignIdList.set(filterCampaignIdList);
            });
            reCampaignIdList = currentCampaignIdList.get();
            log.info("[{}][getCampaignIdListByTransactionCode][reCampaignIdList : {}]", CLASS_NAME, reCampaignIdList);
        }

        if (CollectionUtils.isEmpty(reCampaignIdList)) {
            new NaviException(FabricResponseCode.DATA_NOT_FOUND);
        }
        return reCampaignIdList;
    }

    private List<QueryCampaignRuleSettingExtraConditionDto.RuleSetting> getRuleSettingListByTransactionCode(TransactionCodeEnum transactionCodeEnum, QueryRuleEngineTreeByConditionRequestBo queryRuleEngineTreeByConditionRequestBo) {
        List<QueryCampaignRuleSettingExtraConditionDto.RuleSetting> ruleSettingList = new ArrayList<>();
        switch (transactionCodeEnum) {
            case TRANSFER -> {

            }
            case EXCHANGE -> {
                if (ObjectUtils.isEmpty(queryRuleEngineTreeByConditionRequestBo.getExchangeRq())) {
                    throw new NaviException(FabricResponseCode.INVALID_DATA);
                }
                QueryRuleEngineTreeByConditionRequestBo.ExchangeRqBo exchangeRqBo = queryRuleEngineTreeByConditionRequestBo.getExchangeRq();
                QueryCampaignRuleSettingExtraConditionDto.RuleSetting fromCurrencyRuleSetting = QueryCampaignRuleSettingExtraConditionDto.RuleSetting.builder()
                        .ruleName(ExchangeExtraRuleEnum.FROM_CURRENCY.getCode())
                        .ruleValue(ObjectUtils.isEmpty(exchangeRqBo.getFromCurrency()) ? null : exchangeRqBo.getFromCurrency())
                        .build();
                QueryCampaignRuleSettingExtraConditionDto.RuleSetting toCurrencyRuleSetting = QueryCampaignRuleSettingExtraConditionDto.RuleSetting.builder()
                        .ruleName(ExchangeExtraRuleEnum.TO_CURRENCY.getCode())
                        .ruleValue(ObjectUtils.isEmpty(exchangeRqBo.getToCurrency()) ? null : exchangeRqBo.getToCurrency())
                        .build();
//                QueryCampaignRuleSettingExtraConditionDto.RuleSetting sourceSystemRuleSetting = QueryCampaignRuleSettingExtraConditionDto.RuleSetting.builder()
//                        .ruleName(ExchangeExtraRuleEnum.SOURCE_SYSTEM.getCode())
//                        .ruleValue(ObjectUtils.isEmpty(exchangeRqBo.getSourceSystem()) ? null : exchangeRqBo.getSourceSystem())
//                        .build();
//                QueryCampaignRuleSettingExtraConditionDto.RuleSetting branchRuleSetting = QueryCampaignRuleSettingExtraConditionDto.RuleSetting.builder()
//                        .ruleName(ExchangeExtraRuleEnum.BRANCH.getCode())
//                        .ruleValue(ObjectUtils.isEmpty(exchangeRqBo.getBranch()) ? null : exchangeRqBo.getBranch())
//                        .build();
                ruleSettingList.add(fromCurrencyRuleSetting);
                ruleSettingList.add(toCurrencyRuleSetting);
//                ruleSettingList.add(sourceSystemRuleSetting);
//                ruleSettingList.add(branchRuleSetting);
            }
        }
        return ruleSettingList;
    }

    @Override
    public QueryRuleRsBo queryRule(QueryRuleRqBo queryRuleRqBo) {
        QueryCampaignRuleConditionDto queryCampaignRuleConditionDto = new QueryCampaignRuleConditionDto();
        List<CampaignRuleDto> campaignRuleDtoList = campaignRuleDao.queryCampaignRule(queryCampaignRuleConditionDto);
        List<QueryRuleRsBo.RuleBo> ruleBoList = new ArrayList<>();
        campaignRuleDtoList.forEach(x -> {
            QueryRuleRsBo.RuleBo ruleBo = new QueryRuleRsBo.RuleBo();
            ruleBo.setRuleId(x.getId());
            ruleBo.setTransactionCode(x.getTransactionCode());
            ruleBo.setRuleName(x.getRuleName());
            ruleBo.setRuleType(x.getRuleType());
            ruleBo.setRuleDefaultValue(x.getRuleDefaultValue());
            ruleBoList.add(ruleBo);
        });
        QueryRuleRsBo queryRuleRsBo = new QueryRuleRsBo();
        queryRuleRsBo.setRuleList(ruleBoList);
        return queryRuleRsBo;
    }


}
