package com.ctbcbank.navi.mid.campaign.management.service.customerlist.query;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignCustomerListDao;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlist.CampaignCustomerListDto;
import com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlist.QueryCampaignCustomerListConditionDto;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomerListQueryServiceImpl implements CustomerListQueryService {
    private final String CLASS_NAME = CustomerListQueryServiceImpl.class.getSimpleName();
    private final CampaignCustomerListDao campaignCustomerListDao;

    @Override
    public CustomerListQueryRsBo query(CustomerListQueryRqBo customerListQueryRqBo) {
        QueryCampaignCustomerListConditionDto queryCampaignCustomerListConditionDto = new QueryCampaignCustomerListConditionDto();
        queryCampaignCustomerListConditionDto.setStatusList(customerListQueryRqBo.getStatusList());
        List<CampaignCustomerListDto> campaignCustomerListDtoList = campaignCustomerListDao.queryCampaignCustomerList(queryCampaignCustomerListConditionDto);

        CustomerListQueryRsBo customerListQueryRsBo = new CustomerListQueryRsBo();
        customerListQueryRsBo.setCustomerListInfoList(getCustomerListInfoBoList(campaignCustomerListDtoList));
        return customerListQueryRsBo;
    }

    private List<CustomerListQueryRsBo.CustomerListInfoBo> getCustomerListInfoBoList(List<CampaignCustomerListDto> campaignCustomerListDtoList) {
        List<CustomerListQueryRsBo.CustomerListInfoBo> customerListInfoBoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignCustomerListDtoList)) {
            return customerListInfoBoList;
        }
        customerListInfoBoList = campaignCustomerListDtoList.stream().map(x -> {
            CustomerListQueryRsBo.CustomerListInfoBo customerListInfoBo = new CustomerListQueryRsBo.CustomerListInfoBo();
            customerListInfoBo.setCustomerListNo(x.getCustomerListNo());
            customerListInfoBo.setName(x.getName());
            customerListInfoBo.setStatus(x.getStatus());
            customerListInfoBo.setCreateEmployeeNo(x.getCreateEmployeeNo());
            customerListInfoBo.setTotalCount(x.getTotalCount());
            return customerListInfoBo;
        }).toList();
        return customerListInfoBoList;
    }
}
