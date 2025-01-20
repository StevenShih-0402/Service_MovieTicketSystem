package com.ctbcbank.navi.mid.campaign.management.controller.customerlist;

import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.create.CustomerListCreateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.create.CustomerListCreateRs;
import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.query.CustomerListQueryRq;
import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.query.CustomerListQueryRs;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.create.CustomerListCreateConverter;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.create.CustomerListCreateRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.create.CustomerListCreateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.create.CustomerListCreateService;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.query.CustomerListQueryConverter;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.query.CustomerListQueryRqBo;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.query.CustomerListQueryRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.query.CustomerListQueryService;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import com.ibm.cbmp.fabric.web.api.annotation.PostApiMapping;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/customer-list/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerListController {

    private final CustomerListCreateService customerListCreateService;
    private final CustomerListQueryService customerListQueryService;

    @Operation(summary = "新增 客戶名單", description = "新增 客戶名單")
    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CustomerListCreateRs create(@Valid @ModelAttribute CustomerListCreateRq customerListCreateRq) {
        // 限制csv檔案
        if (!CollectionUtils.isEmpty(customerListCreateRq.getFileList())) {
            List<MultipartFile> fileList = customerListCreateRq.getFileList();
            List<MultipartFile> nonCsvFileList = fileList.stream()
                    .filter(x -> !StringUtils.equalsAnyIgnoreCase("text/csv", x.getContentType()) || StringUtils.isBlank(x.getName()) || !x.getOriginalFilename().toLowerCase().endsWith(".csv"))
                    .toList();
            if (!CollectionUtils.isEmpty(nonCsvFileList)) {
                throw new NaviException(FabricResponseCode.INVALID_DATA, "Have non csv file.");
            }
        }
        CustomerListCreateRqBo customerListCreateRqBo = CustomerListCreateConverter.parseRqToRqBo(customerListCreateRq);
        CustomerListCreateRsBo customerListCreateRsBo = customerListCreateService.create(customerListCreateRqBo);
        CustomerListCreateRs customerListCreateRs = CustomerListCreateConverter.parseRsBoToRs(customerListCreateRsBo);
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
