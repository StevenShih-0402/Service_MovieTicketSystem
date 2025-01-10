package com.ctbcbank.navi.mid.campaign.management.controller.customerlistdetail;

import com.ctbcbank.navi.mid.campaign.management.controller.customerlistdetail.payload.querybycustomerlistno.CustomerListDetailQueryByCustomerListNoRq;
import com.ctbcbank.navi.mid.campaign.management.controller.customerlistdetail.payload.querybycustomerlistno.CustomerListDetailQueryByCustomerListNoRs;
import com.ctbcbank.navi.mid.campaign.management.service.customerlisdetail.querybycustomerlistno.CustomerListDetailQueryByCustomerListNoConverter;
import com.ctbcbank.navi.mid.campaign.management.service.customerlisdetail.querybycustomerlistno.CustomerListDetailQueryByCustomerListNoRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.customerlisdetail.querybycustomerlistno.CustomerListDetailQueryByCustomerListNoRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.customerlisdetail.querybycustomerlistno.CustomerListDetailQueryByCustomerListNoService;
import com.ibm.cbmp.fabric.web.api.annotation.PostApiMapping;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/customer-list-detail/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerListDetailController {
    private final CustomerListDetailQueryByCustomerListNoService customerListDetailQueryByCustomerListNoService;

    @Operation(summary = "查詢 客戶名單細項清單 By 客戶名單編號", description = "查詢 客戶名單細項清單 By 客戶名單編號")
    @PostApiMapping(value = "query/by-customer-list-no")
    CustomerListDetailQueryByCustomerListNoRs queryByCustomerListNo(@Valid @RequestBody CustomerListDetailQueryByCustomerListNoRq customerListDetailQueryByCustomerListNoRq) {
        CustomerListDetailQueryByCustomerListNoRqBo customerListDetailQueryByCustomerListNoRqBo = CustomerListDetailQueryByCustomerListNoConverter.parseRqToRqBo(
                customerListDetailQueryByCustomerListNoRq);
        CustomerListDetailQueryByCustomerListNoRsBo customerListDetailQueryByCustomerListNoRsBo = customerListDetailQueryByCustomerListNoService.queryByCustomerListNo(
                customerListDetailQueryByCustomerListNoRqBo);
        CustomerListDetailQueryByCustomerListNoRs customerListDetailQueryByCustomerListNoRs = CustomerListDetailQueryByCustomerListNoConverter.parseRsBoToRs(
                customerListDetailQueryByCustomerListNoRsBo);
        return customerListDetailQueryByCustomerListNoRs;
    }
}
