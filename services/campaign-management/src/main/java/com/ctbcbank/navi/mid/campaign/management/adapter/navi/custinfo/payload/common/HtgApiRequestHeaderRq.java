package com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "HTG Header Request Payload", description = "HTG Header Request Payload")
public class HtgApiRequestHeaderRq implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "sessionId", description = "sessionId", minLength = 1, example = "PCMS203AD-X4GCnbZ9oNHgi65191KSWZ49751")
    @NotBlank(message = "sessionId必填")
    private String sessionId;

    @Schema(title = "cookieId", description = "cookieId", minLength = 1, example = "長度規格 TODO")
    @NotBlank(message = "cookieId必填")
    private String cookieId;

    @Schema(title = "系統代碼", description = "系統代碼", minLength = 1, example = "CSDP-CRI")
    @NotBlank(message = "系統必填")
    private String sourceSystem;
}
