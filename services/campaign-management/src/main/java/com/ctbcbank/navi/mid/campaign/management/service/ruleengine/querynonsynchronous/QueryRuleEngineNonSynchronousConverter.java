package com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querynonsynchronous;

import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineBasicInformation;
import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineNonSynchronousRq;
import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineNonSynchronousRs;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;

public class QueryRuleEngineNonSynchronousConverter {
    public static QueryRuleEngineNonSynchronousRqBo parseRqToBoRq(QueryRuleEngineNonSynchronousRq queryRuleEngineNonSynchronousRq) {
        QueryRuleEngineNonSynchronousRqBo queryRuleEngineNonSynchronousRqBo = new QueryRuleEngineNonSynchronousRqBo();
        queryRuleEngineNonSynchronousRqBo.setQueryRuleEngineBasicInformationBo(getQueryRuleEngineBasicInformationBo(queryRuleEngineNonSynchronousRq.getQueryRuleEngineBasicInformation()));
        return queryRuleEngineNonSynchronousRqBo;
    }

    public static QueryRuleEngineNonSynchronousRs parseBoRsToRs(QueryRuleEngineNonSynchronousRsBo queryRuleEngineNonSynchronousRsBo) {
        QueryRuleEngineNonSynchronousRs queryRuleEngineNonSynchronousRs = new QueryRuleEngineNonSynchronousRs();
        queryRuleEngineNonSynchronousRs.setRuleEngineTree(queryRuleEngineNonSynchronousRsBo.getRuleEngineTree());
        return queryRuleEngineNonSynchronousRs;
    }

    private static QueryRuleEngineBasicInformationBo getQueryRuleEngineBasicInformationBo(QueryRuleEngineBasicInformation queryRuleEngineBasicInformation) {
        QueryRuleEngineBasicInformationBo queryRuleEngineBasicInformationBo = new QueryRuleEngineBasicInformationBo();
        queryRuleEngineBasicInformationBo.setIpNo(queryRuleEngineBasicInformation.getIpNo());
        queryRuleEngineBasicInformationBo.setTransactionCode(queryRuleEngineBasicInformation.getTransactionCode());
        queryRuleEngineBasicInformationBo.setTranDateTime(queryRuleEngineBasicInformation.getTranDateTime());
        queryRuleEngineBasicInformationBo.setEntityType(queryRuleEngineBasicInformation.getEntityType());
        queryRuleEngineBasicInformationBo.setEntityReferenceNumber(queryRuleEngineBasicInformation.getEntityReferenceNumber());
        queryRuleEngineBasicInformationBo.setIncrement(queryRuleEngineBasicInformation.getIncrement());
        if (ObjectUtils.isNotEmpty(queryRuleEngineBasicInformation.getTransferRq())) {
            QueryRuleEngineBasicInformationBo.TransferRqBo transferRqBo = new QueryRuleEngineBasicInformationBo.TransferRqBo();
            queryRuleEngineBasicInformationBo.setTransferRq(transferRqBo);
        }
        if (ObjectUtils.isNotEmpty(queryRuleEngineBasicInformation.getExchangeRq())) {
            QueryRuleEngineBasicInformationBo.ExchangeRqBo exchangeRqBo = new QueryRuleEngineBasicInformationBo.ExchangeRqBo();
            QueryRuleEngineBasicInformation.ExchangeRq exchangeRq = queryRuleEngineBasicInformation.getExchangeRq();
            exchangeRqBo.setFromCurrency(exchangeRq.getFromCurrency());
            exchangeRqBo.setToCurrency(exchangeRq.getToCurrency());
            exchangeRqBo.setBranch(exchangeRq.getBranch());
            exchangeRqBo.setSourceSystem(exchangeRq.getSourceSystem());
            queryRuleEngineBasicInformationBo.setExchangeRq(exchangeRqBo);
        }
        return queryRuleEngineBasicInformationBo;
    }
}
