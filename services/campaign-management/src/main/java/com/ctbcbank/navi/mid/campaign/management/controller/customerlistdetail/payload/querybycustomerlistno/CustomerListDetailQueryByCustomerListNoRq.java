package com.ctbcbank.navi.mid.campaign.management.controller.customerlistdetail.payload.querybycustomerlistno;

import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListDetailQueryByCustomerListNoRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(title = "客戶名單編號", description = "客戶名單編號", example = "8158a3ed-ec33-4007-babd-c59f9a828c60")
    private String customerListNo;

    @NotNull
    @Max(value = 1000, message = "size cannot exceed 1000")
    @Min(value = 1, message = "size must be at least 1")
    @Schema(title = "每頁大小", description = "每頁大小", example = "1000")
    private Integer size;

    @NotNull
    @Schema(title = "當前頁數", description = "當前頁數,0為第一頁...以此類推", example = "0")
    private Integer number;

}
