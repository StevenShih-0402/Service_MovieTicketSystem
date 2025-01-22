package com.ctbcbank.navi.mid.campaign.management.service.customerlist.create.impl;

import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.CustomerInfoManagementAdapter;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload.QueryInvolvedPartyByConditionV2Rq;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload.QueryInvolvedPartyByConditionV2Rs;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload.common.HtgApiRequestHeaderRq;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCustomerListDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCustomerListDetailDao;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlist.CampaignCustomerListDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlist.QueryCampaignCustomerListConditionDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail.CampaignCustomerListDetailDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail.QueryCampaignCustomerListDetailConditionDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.create.CustomerListProcessDataService;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import com.ibm.cbmp.fabric.web.utils.ApiExceptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomerListProcessDataServiceImpl implements CustomerListProcessDataService {
    private final String CLASS_NAME = CustomerListProcessDataServiceImpl.class.getSimpleName();
    private final CampaignCustomerListDetailDao campaignCustomerListDetailDao;
    private final CampaignCustomerListDao campaignCustomerListDao;
    private final CustomerInfoManagementAdapter customerInfoManagementAdapter;
    // 重試次數
    private static final int RETRY_ATTEMPTS = 3;

    @Override
    @Async
    public void processData(String customerListNo) {
        QueryCampaignCustomerListConditionDto queryCampaignCustomerListConditionDto = new QueryCampaignCustomerListConditionDto();
        queryCampaignCustomerListConditionDto.setCustomerListNo(customerListNo);
        List<CampaignCustomerListDto> campaignCustomerListDtoList = campaignCustomerListDao.queryCampaignCustomerList(queryCampaignCustomerListConditionDto);
        if (CollectionUtils.isEmpty(campaignCustomerListDtoList) || campaignCustomerListDtoList.size() > 1) {
            throw new NaviException(FabricResponseCode.INVALID_DATA, "CustomerListNo: " + customerListNo + ", Data Not Found || size > 1");
        }
        CampaignCustomerListDto campaignCustomerListDto = campaignCustomerListDtoList.get(0);
        // 更新為查詢中
        campaignCustomerListDto.setStatus(CampaignCustomerListStatusEnum.IN_PROGRESS);
        campaignCustomerListDao.saveCampaignCustomerList(campaignCustomerListDto);

        // 分頁查詢
        // 當前頁數
        int number = 0;
        // 每頁大小
        int size = 100;
        String sortDirection = "asc";
        QueryCampaignCustomerListDetailConditionDto queryCampaignCustomerListDetailConditionDto = new QueryCampaignCustomerListDetailConditionDto();
        queryCampaignCustomerListDetailConditionDto.setCustomerListNo(customerListNo);
        // 狀態為未查詢或查詢中
        List<CampaignCustomerListStatusEnum> statusList = Stream.of(CampaignCustomerListStatusEnum.NOT_QUERIED, CampaignCustomerListStatusEnum.IN_PROGRESS).collect(Collectors.toList());
        queryCampaignCustomerListDetailConditionDto.setStatusList(statusList);
        while (true) {
            Page<CampaignCustomerListDetailDto> queryResult = campaignCustomerListDetailDao.queryCampaignCustomerListDetail(
                    queryCampaignCustomerListDetailConditionDto, number, size, sortDirection);
            log.info("[{}][processData][TotalElements: {}]", CLASS_NAME, queryResult.getTotalElements());
            List<CampaignCustomerListDetailDto> campaignCustomerListDetailDtoList = queryResult.getContent();
            if (CollectionUtils.isEmpty(campaignCustomerListDetailDtoList)) {
                log.info("[{}][processData][campaignCustomerListDetailDtoList is empty(break).]", CLASS_NAME);
                break;
            }
            getIpNo(campaignCustomerListDetailDtoList);
        }

        // 搜尋 By 客戶名單編號、已查詢且重試3次失敗
        queryCampaignCustomerListDetailConditionDto = new QueryCampaignCustomerListDetailConditionDto();
        queryCampaignCustomerListDetailConditionDto.setCustomerListNo(customerListNo);
        statusList = Stream.of(CampaignCustomerListStatusEnum.COMPLETED_RETRY_FAILED).collect(Collectors.toList());
        queryCampaignCustomerListDetailConditionDto.setStatusList(statusList);
        Page<CampaignCustomerListDetailDto> queryResult = campaignCustomerListDetailDao.queryCampaignCustomerListDetail(queryCampaignCustomerListDetailConditionDto, number, size, sortDirection);
        // 更新狀態為已查詢
        campaignCustomerListDto.setStatus(CampaignCustomerListStatusEnum.COMPLETED);
        if (queryResult.getTotalElements() > 0) {
            // 更新狀態為已查詢且重試3次失敗
            campaignCustomerListDto.setStatus(CampaignCustomerListStatusEnum.COMPLETED_RETRY_FAILED);
        }
        campaignCustomerListDao.saveCampaignCustomerList(campaignCustomerListDto);
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
            log.info("[{}][getIpNo][process id: {}({}/{}) start...]", CLASS_NAME, campaignCustomerListDetailDto.getId(), count, totalCount);
            try {
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

                QueryInvolvedPartyByConditionV2Rs queryInvolvedPartyByConditionV2Rs = customerInfoManagementAdapter.queryInvolvedPartyByCondition(
                        queryInvolvedPartyByConditionV2Rq, htgApiRequestHeaderRq, RETRY_ATTEMPTS);
                ApiExceptionUtils.validAndThrowNaviErrorException(queryInvolvedPartyByConditionV2Rs);
                List<BigInteger> ipNoList = queryInvolvedPartyByConditionV2Rs.getInvolvedParties().stream().map(QueryInvolvedPartyByConditionV2Rs.QueryInvolvedPartyItemV2Rs::getInvolvedPartyNo)
                        .toList();
                // 更新IP NO清單
                campaignCustomerListDetailDto.setIpNoList(ipNoList);
                campaignCustomerListDetailDto.setMessage(null);
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
                // 更新狀態為已查詢
                campaignCustomerListDetailDto.setStatus(CampaignCustomerListStatusEnum.COMPLETED);
                // 清空錯誤訊息
                campaignCustomerListDetailDto.setMessage(null);
            } catch (Exception ex) {
                log.error("[{}][getIpNo][process id: {}({}/{}) error: {}]", CLASS_NAME, campaignCustomerListDetailDto.getId(), count, totalCount, ex.getMessage());
                // 更新狀態為已查詢且重試3次失敗
                campaignCustomerListDetailDto.setStatus(CampaignCustomerListStatusEnum.COMPLETED_RETRY_FAILED);
                // 更新錯誤訊息
                campaignCustomerListDetailDto.setMessage(ex.getMessage());
            }
            campaignCustomerListDetailDao.saveCampaignCustomerListDetail(campaignCustomerListDetailDto);


            log.info("[{}][getIpNo][process id: {}({}/{}) end.]", CLASS_NAME, campaignCustomerListDetailDto.getId(), count, totalCount);
            count++;
        }


    }

}
