package com.ctbcbank.navi.mid.campaign.management.service.customerlist.create.impl;

import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.CustomerInfoManagementAdapter;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload.QueryInvolvedPartyByConditionV2Rq;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload.QueryInvolvedPartyByConditionV2Rs;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload.common.HtgApiRequestHeaderRq;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCustomerListDetailDao;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail.CampaignCustomerListDetailDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.create.CustomerListProcessDataService;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import com.ibm.cbmp.fabric.web.utils.ApiExceptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomerListProcessDataServiceImpl implements CustomerListProcessDataService {
    private final String CLASS_NAME = CustomerListProcessDataServiceImpl.class.getSimpleName();
    private final CampaignCustomerListDetailDao campaignCustomerListDetailDao;
    private final CustomerInfoManagementAdapter customerInfoManagementAdapter;

    @Override
    @Async
    @Transactional
    public void processData(String customerListNo) {
        // 分頁查詢
        // 當前頁數
        int number = 0;
        // 總頁數，預設為最大
        int totalPages = Integer.MAX_VALUE;
        // 每頁大小
        int size = 1000;
        String sortDirection = "asc";
        Page<CampaignCustomerListDetailDto> firstQueryResult = campaignCustomerListDetailDao.queryCampaignCustomerListDetailByCustomerListNo(customerListNo, number, size, sortDirection);
        totalPages = firstQueryResult.getTotalPages();
        log.info("[{}][processData][totalPages: {}]", CLASS_NAME, totalPages);
        for (; number < totalPages; number++) {
            log.info("[{}][processData][page: {}/{}]", CLASS_NAME, number, totalPages);
            Page<CampaignCustomerListDetailDto> queryResult = campaignCustomerListDetailDao.queryCampaignCustomerListDetailByCustomerListNo(customerListNo, number, size, sortDirection);
            List<CampaignCustomerListDetailDto> campaignCustomerListDetailDtoList = queryResult.getContent();
            getIpNo(campaignCustomerListDetailDtoList);
        }
        log.info("[{}][processData][processData Data end.]", CLASS_NAME);

    }

    /**
     * 處理客戶名單細項清單，透過CIM取得IP_NO
     *
     * @param campaignCustomerListDetailDtoList
     */
    public void getIpNo(List<CampaignCustomerListDetailDto> campaignCustomerListDetailDtoList) {
        int totalCount = campaignCustomerListDetailDtoList.size();
        log.info("[{}][getIpNo][totalCount: {}]", CLASS_NAME, totalCount);
        int count = 1;
        for (CampaignCustomerListDetailDto campaignCustomerListDetailDto : campaignCustomerListDetailDtoList) {
            log.info("[{}][getIpNo][process {}/{} start...]", CLASS_NAME, count, totalCount);
            // 更新狀態為查詢中
            campaignCustomerListDetailDto.setStatus(CampaignCustomerListStatusEnum.IN_PROGRESS);
            campaignCustomerListDetailDto = campaignCustomerListDetailDao.saveCampaignCustomerListDetail(campaignCustomerListDetailDto);

            // Call CIM取的IP NO
            QueryInvolvedPartyByConditionV2Rq queryInvolvedPartyByConditionV2Rq = new QueryInvolvedPartyByConditionV2Rq();

            queryInvolvedPartyByConditionV2Rq.setIdentificationNo(campaignCustomerListDetailDto.getIdNo());
            queryInvolvedPartyByConditionV2Rq.setName(campaignCustomerListDetailDto.getName());
            HtgApiRequestHeaderRq htgApiRequestHeaderRq = new HtgApiRequestHeaderRq();
            htgApiRequestHeaderRq.setCookieId(StringUtils.HYPHEN);
            htgApiRequestHeaderRq.setSessionId(StringUtils.HYPHEN);
            htgApiRequestHeaderRq.setSourceSystem(StringUtils.HYPHEN);
            try {
                QueryInvolvedPartyByConditionV2Rs queryInvolvedPartyByConditionV2Rs = customerInfoManagementAdapter.queryInvolvedPartyByCondition(
                        queryInvolvedPartyByConditionV2Rq, htgApiRequestHeaderRq);
                ApiExceptionUtils.validAndThrowNaviErrorException(queryInvolvedPartyByConditionV2Rs);
                List<BigInteger> ipNoList = queryInvolvedPartyByConditionV2Rs.getInvolvedParties().stream().map(QueryInvolvedPartyByConditionV2Rs.QueryInvolvedPartyItemV2Rs::getInvolvedPartyNo)
                        .toList();
                // 更新IP NO清單
                campaignCustomerListDetailDto.setIpNoList(ipNoList);
                if (!CollectionUtils.isEmpty(ipNoList)) {
                    if (ipNoList.size() == 1) {
                        // 更新為單一IP NO
                        campaignCustomerListDetailDto.setIsSingleIpNo(true);
                        // 更新預設選定的IP NO
                        campaignCustomerListDetailDto.setChosenIpNo(ipNoList.get(0));
                    } else {
                        // 更新為非單一IP NO
                        campaignCustomerListDetailDto.setIsSingleIpNo(false);
                    }
                }
            } catch (Exception ex) {
                log.error("[{}][getIpNo][Exception: {}]", CLASS_NAME, ex);
                campaignCustomerListDetailDto.setMessage(ex.getMessage());
            } finally {
                // 更新狀態為已查詢
                campaignCustomerListDetailDto.setStatus(CampaignCustomerListStatusEnum.COMPLETED);
                campaignCustomerListDetailDao.saveCampaignCustomerListDetail(campaignCustomerListDetailDto);
            }
            log.info("[{}][getIpNo][process {}/{} end.]", CLASS_NAME, count, totalCount);
            count++;
        }


    }

}
