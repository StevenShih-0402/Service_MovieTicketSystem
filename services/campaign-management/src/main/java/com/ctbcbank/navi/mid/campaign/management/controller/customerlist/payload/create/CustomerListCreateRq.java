package com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.create;

import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListCreateRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(title = "客戶名單名稱", description = "客戶名單名稱", example = "客戶名單名稱")
    private String name;

    @NotEmpty
    @Schema(title = "檔案清單", description = "檔案清單，僅支援CSV格式")
    List<MultipartFile> fileList;

    @NotBlank
    @Schema(title = "員工編號-創建者", description = "員工編號-創建者", example = "A001")
    private String createEmployeeNo;

}
