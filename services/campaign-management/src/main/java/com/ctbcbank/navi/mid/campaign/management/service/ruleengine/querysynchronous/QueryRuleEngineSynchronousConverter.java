package com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querysynchronous;

import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineBasicInformation;
import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineSynchronousRq;
import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineSynchronousRs;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;

public class QueryRuleEngineSynchronousConverter {

    public static QueryRuleEngineSynchronousRqBo parseRqToBoRq(QueryRuleEngineSynchronousRq queryRuleEngineSynchronousRq) {
        QueryRuleEngineSynchronousRqBo queryRuleEngineSynchronousRqBo = new QueryRuleEngineSynchronousRqBo();
        queryRuleEngineSynchronousRqBo.setQueryRuleEngineBasicInformationBo(getQueryRuleEngineBasicInformationBo(queryRuleEngineSynchronousRq.getQueryRuleEngineBasicInformation()));
        return queryRuleEngineSynchronousRqBo;
    }

    public static QueryRuleEngineSynchronousRs parseRsBoToRs(QueryRuleEngineSynchronousRsBo queryRuleEngineSynchronousRsBo) {
        QueryRuleEngineSynchronousRs queryRuleEngineSynchronousRs = new QueryRuleEngineSynchronousRs();
        queryRuleEngineSynchronousRs.setRuleEngineTree(queryRuleEngineSynchronousRsBo.getRuleEngineTree());
        return queryRuleEngineSynchronousRs;
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
        queryRuleEngineBasicInformationBo.setDynamicFields(queryRuleEngineBasicInformation.getDynamicFields());
        return queryRuleEngineBasicInformationBo;
    }

}
