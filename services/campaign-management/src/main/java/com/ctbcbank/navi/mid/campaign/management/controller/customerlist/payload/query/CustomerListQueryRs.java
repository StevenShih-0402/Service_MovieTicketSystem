package com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.query;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListQueryRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "客戶名單清單", description = "客戶名單清單")
    private List<CustomerListInfo> customerListInfoList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerListInfo {

        @Schema(title = "客戶名單編號", description = "客戶名單編號", example = "8158a3ed-ec33-4007-babd-c59f9a828c60")
        private String customerListNo;

        @Schema(title = "客戶名單名稱", description = "客戶名單名稱", example = "客戶名單名稱")
        private String name;

        @Schema(title = "狀態", description = "狀態", example = "COMPLETED")
        private CampaignCustomerListStatusEnum status;

        @Schema(title = "員工編號-創建者", description = "員工編號-創建者", example = "CreateEmployeeNo001")
        private String createEmployeeNo;

        @Schema(title = "總筆數", description = "總筆數", example = "1000")
        private BigInteger totalCount;
    }
}
