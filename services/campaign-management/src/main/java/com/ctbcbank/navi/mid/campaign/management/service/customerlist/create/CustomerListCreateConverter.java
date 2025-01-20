package com.ctbcbank.navi.mid.campaign.management.service.customerlist.create;

import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.create.CustomerListCreateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.create.CustomerListCreateRs;

public class CustomerListCreateConverter {

    public static CustomerListCreateRqBo parseRqToRqBo(CustomerListCreateRq customerListCreateRq) {
        CustomerListCreateRqBo customerListCreateRqBo = new CustomerListCreateRqBo();
        customerListCreateRqBo.setName(customerListCreateRq.getName());
        customerListCreateRqBo.setFileList(customerListCreateRq.getFileList());
        customerListCreateRqBo.setCreateEmployeeNo(customerListCreateRq.getCreateEmployeeNo());
        return customerListCreateRqBo;
    }

    public static CustomerListCreateRs parseRsBoToRs(CustomerListCreateRsBo customerListCreateRsBo) {
        CustomerListCreateRs customerListCreateRs = new CustomerListCreateRs();
        customerListCreateRs.setCustomerListNo(customerListCreateRsBo.getCustomerListNo());
        return customerListCreateRs;
    }

}
