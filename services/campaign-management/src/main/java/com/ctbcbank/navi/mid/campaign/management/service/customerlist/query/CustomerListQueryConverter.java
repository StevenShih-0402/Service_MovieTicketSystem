package com.ctbcbank.navi.mid.campaign.management.service.customerlist.query;

import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.query.CustomerListQueryRq;
import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.query.CustomerListQueryRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerListQueryConverter {

    public static CustomerListQueryRqBo parseRqToRqBo(CustomerListQueryRq customerListQueryRq) {
        CustomerListQueryRqBo customerListQueryRqBo = new CustomerListQueryRqBo();
        customerListQueryRqBo.setStatusList(customerListQueryRq.getStatusList());
        return customerListQueryRqBo;
    }

    public static CustomerListQueryRs parseRsBoToRs(CustomerListQueryRsBo customerListQueryRsBo) {
        CustomerListQueryRs customerListQueryRs = new CustomerListQueryRs();
        customerListQueryRs.setCustomerListInfoList(getCustomerListInfoList(customerListQueryRsBo.getCustomerListInfoList()));
        return customerListQueryRs;
    }

    private static List<CustomerListQueryRs.CustomerListInfo> getCustomerListInfoList(List<CustomerListQueryRsBo.CustomerListInfoBo> customerListInfoBoList) {
        List<CustomerListQueryRs.CustomerListInfo> customerListInfoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(customerListInfoBoList)) {
            return customerListInfoList;
        }
        customerListInfoList = customerListInfoBoList.stream().map(x -> {
            CustomerListQueryRs.CustomerListInfo customerListInfo = new CustomerListQueryRs.CustomerListInfo();
            customerListInfo.setCustomerListNo(x.getCustomerListNo());
            customerListInfo.setName(x.getName());
            customerListInfo.setStatus(x.getStatus());
            customerListInfo.setCreateEmployeeNo(x.getCreateEmployeeNo());
            customerListInfo.setTotalCount(x.getTotalCount());
            return customerListInfo;
        }).toList();
        return customerListInfoList;
    }
}
