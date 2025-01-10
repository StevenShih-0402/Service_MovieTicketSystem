package com.ctbcbank.navi.mid.campaign.management.controller.customerlist;

import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.create.CustomerListCreateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.create.CustomerListCreateRs;
import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.query.CustomerListQueryRq;
import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.query.CustomerListQueryRs;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.query.CustomerListQueryConverter;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.query.CustomerListQueryRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.query.CustomerListQueryRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.query.CustomerListQueryService;
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
@RequestMapping(path = "v1/customer-list/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerListController {

    private final CustomerListQueryService customerListQueryService;

    @Operation(summary = "新增 客戶名單", description = "新增 客戶名單")
    @PostApiMapping(value = "create")
    CustomerListCreateRs create(@Valid @RequestBody CustomerListCreateRq customerListCreateRq) {
        CustomerListCreateRs customerListCreateRs = new CustomerListCreateRs();
        // 建完TB_CAMPAIGN_CUSTOMER_LIST回傳編號
        // 開另外一條處理檔案，並打CIM取IP_NO
        return customerListCreateRs;
    }

    @Operation(summary = "查詢 客戶名單清單", description = "查詢 客戶名單清單")
    @PostApiMapping(value = "query")
    CustomerListQueryRs query(@Valid @RequestBody CustomerListQueryRq customerListQueryRq) {
        CustomerListQueryRqBo customerListQueryRqBo = CustomerListQueryConverter.parseRqToRqBo(customerListQueryRq);
        CustomerListQueryRsBo customerListQueryRsBo = customerListQueryService.query(customerListQueryRqBo);
        CustomerListQueryRs customerListQueryRs = CustomerListQueryConverter.parseRsBoToRs(customerListQueryRsBo);
        return customerListQueryRs;
    }
}
