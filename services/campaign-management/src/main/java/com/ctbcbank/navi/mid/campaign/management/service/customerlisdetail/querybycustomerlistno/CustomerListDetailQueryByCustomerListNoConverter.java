package com.ctbcbank.navi.mid.campaign.management.service.customerlisdetail.querybycustomerlistno;

import com.ctbcbank.navi.mid.campaign.management.controller.customerlistdetail.payload.querybycustomerlistno.CustomerListDetailQueryByCustomerListNoRq;
import com.ctbcbank.navi.mid.campaign.management.controller.customerlistdetail.payload.querybycustomerlistno.CustomerListDetailQueryByCustomerListNoRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerListDetailQueryByCustomerListNoConverter {

    public static CustomerListDetailQueryByCustomerListNoRqBo parseRqToRqBo(CustomerListDetailQueryByCustomerListNoRq customerListDetailQueryByCustomerListNoRq) {
        CustomerListDetailQueryByCustomerListNoRqBo customerListDetailQueryByCustomerListNoRqBo = new CustomerListDetailQueryByCustomerListNoRqBo();
        customerListDetailQueryByCustomerListNoRqBo.setCustomerListNo(customerListDetailQueryByCustomerListNoRq.getCustomerListNo());
        customerListDetailQueryByCustomerListNoRqBo.setSize(customerListDetailQueryByCustomerListNoRq.getSize());
        customerListDetailQueryByCustomerListNoRqBo.setNumber(customerListDetailQueryByCustomerListNoRq.getNumber());
        return customerListDetailQueryByCustomerListNoRqBo;
    }

    public static CustomerListDetailQueryByCustomerListNoRs parseRsBoToRs(CustomerListDetailQueryByCustomerListNoRsBo customerListDetailQueryByCustomerListNoRsBo) {
        CustomerListDetailQueryByCustomerListNoRs customerListDetailQueryByCustomerListNoRs = new CustomerListDetailQueryByCustomerListNoRs();
        customerListDetailQueryByCustomerListNoRs.setTotalElements(customerListDetailQueryByCustomerListNoRsBo.getTotalElements());
        customerListDetailQueryByCustomerListNoRs.setTotalPages(customerListDetailQueryByCustomerListNoRsBo.getTotalPages());
        customerListDetailQueryByCustomerListNoRs.setNumber(customerListDetailQueryByCustomerListNoRsBo.getNumber());
        customerListDetailQueryByCustomerListNoRs.setSize(customerListDetailQueryByCustomerListNoRsBo.getSize());
        customerListDetailQueryByCustomerListNoRs.setCustomerListDetailInfoList(getCustomerListDetailInfoList(customerListDetailQueryByCustomerListNoRsBo.getCustomerListDetailInfoList()));
        return customerListDetailQueryByCustomerListNoRs;

    }

    private static List<CustomerListDetailQueryByCustomerListNoRs.CustomerListDetailInfo> getCustomerListDetailInfoList(List<CustomerListDetailQueryByCustomerListNoRsBo.CustomerListDetailInfoBo> customerListDetailInfoBoList) {
        List<CustomerListDetailQueryByCustomerListNoRs.CustomerListDetailInfo> customerListDetailInfoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(customerListDetailInfoBoList)) {
            return customerListDetailInfoList;
        }
        customerListDetailInfoList = customerListDetailInfoBoList.stream().map(x -> {
            CustomerListDetailQueryByCustomerListNoRs.CustomerListDetailInfo customerListDetailInfo = new CustomerListDetailQueryByCustomerListNoRs.CustomerListDetailInfo();
            customerListDetailInfo.setCustomerListDetailId(x.getCustomerListDetailId());
            customerListDetailInfo.setCustomerListNo(x.getCustomerListNo());
            customerListDetailInfo.setStatus(x.getStatus());
            customerListDetailInfo.setIdNo(x.getIdNo());
            customerListDetailInfo.setName(x.getName());
            customerListDetailInfo.setIpNoList(x.getIpNoList());
            customerListDetailInfo.setMessage(x.getMessage());
            customerListDetailInfo.setIsSingleIpNo(x.getIsSingleIpNo());
            customerListDetailInfo.setChosenIpNo(x.getChosenIpNo());
            customerListDetailInfo.setCreateDttm(x.getCreateDttm());
            customerListDetailInfo.setUpdateDttm(x.getUpdateDttm());
            return customerListDetailInfo;
        }).toList();
        return customerListDetailInfoList;
    }
}
