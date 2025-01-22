package com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo;

import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload.QueryInvolvedPartyByConditionV2Rq;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload.QueryInvolvedPartyByConditionV2Rs;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload.common.HtgApiRequestHeaderRq;
import com.ibm.cbmp.fabric.adapter.htg.constant.HtgConstant;
import com.ibm.cbmp.fabric.foundation.context.NaviGlobalContext;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import com.ibm.cbmp.fabric.web.adapter.NaviRestAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomerInfoManagementAdapter {
    private final String ADAPTER_NAVI_CUSTOMER_INFO_MGMT_DOMAIN = "adapter.navi.customer-info-management.domain";
    private final String CLASS_NAME = CustomerInfoManagementAdapter.class.getSimpleName();

    public QueryInvolvedPartyByConditionV2Rs queryInvolvedPartyByCondition(QueryInvolvedPartyByConditionV2Rq queryInvolvedPartyByConditionV2Rq, HtgApiRequestHeaderRq htgHeader, Integer maxRetry, Integer retryInterval) {
        HttpHeaders headers = NaviRestAdapter.getHttpHeaders();
        if (ObjectUtils.isNotEmpty(htgHeader)) {
            headers.set(HtgConstant.HTG_SOURCE_SYSTEM, htgHeader.getSourceSystem());
            headers.set(HtgConstant.HTG_SESSION_ID, htgHeader.getSessionId());
            headers.set(HtgConstant.HTG_COOKIE_ID, htgHeader.getCookieId());
        }

        return NaviRestAdapter.invoke(
                URI.create(getDomain().concat("/v2/involved-party/by-condition"))
                , headers
                , queryInvolvedPartyByConditionV2Rq
                , new ParameterizedTypeReference<>() {
                }
                , null
                , null
                , maxRetry
                , retryInterval
        );
    }

    /**
     * 取得domain
     */
    private String getDomain() {
        String property = NaviGlobalContext.getProperty(ADAPTER_NAVI_CUSTOMER_INFO_MGMT_DOMAIN);
        if (StringUtils.isBlank(property)) {
            log.error("[{}][getDomain][StringUtils.isBlank(property)]", CLASS_NAME);
            throw new NaviException(FabricResponseCode.UNEXPECTED_ERROR);
        }
        return property;
    }
}
