package com.ctbcbank.navi.mid.campaign.management.controller.customerlistdetail.payload.querybycustomerlistno;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListDetailQueryByCustomerListNoRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "總筆數", description = "總筆數", example = "1")
    private Long totalElements;

    @Schema(title = "總頁數", description = "總頁數", example = "5")
    private Integer totalPages;

    @Schema(title = "當前頁數", description = "當前頁數", example = "1")
    private Integer number;

    @Schema(title = "每頁大小", description = "每頁大小", example = "1")
    private Integer size;

    @Schema(title = "客戶名單細項清單", description = "客戶名單細項清單")
    private List<CustomerListDetailInfo> customerListDetailInfoList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerListDetailInfo {

        @Schema(title = "客戶名單細項唯一編號", description = "客戶名單細項唯一編號", example = "1")
        private BigInteger customerListDetailId;

        @Schema(title = "客戶名單編號", description = "客戶名單編號", example = "8158a3ed-ec33-4007-babd-c59f9a828c60")
        private String customerListNo;

        @Schema(title = "狀態", description = "狀態", example = "COMPLETED")
        private CampaignCustomerListStatusEnum status;

        @Schema(title = "身分證字號", description = "身分證字號", example = "A123456789")
        private String idNo;

        @Schema(title = "姓名", description = "姓名", example = "小麗")
        private String name;

        @Schema(title = "客戶編號清單", description = "客戶編號清單", example = "[1,2,3]")
        private List<BigInteger> ipNoList;

        @Schema(title = "訊息", description = "訊息", example = "")
        private String message;

        @Schema(title = "是否為單一客戶編號", description = "是否為單一客戶編號", example = "true")
        private Boolean isSingleIpNo;

        @Schema(title = "選擇客戶編號", description = "選擇客戶編號", example = "1")
        private BigInteger chosenIpNo;

        @Schema(title = "建立日期時間", description = "建立日期時間", example = "2024-01-01T00:00:00")
        private LocalDateTime createDttm;

        @Schema(title = "異動日期時間", description = "異動日期時間", example = "2024-01-01T00:00:00")
        private LocalDateTime updateDttm;
    }
}
