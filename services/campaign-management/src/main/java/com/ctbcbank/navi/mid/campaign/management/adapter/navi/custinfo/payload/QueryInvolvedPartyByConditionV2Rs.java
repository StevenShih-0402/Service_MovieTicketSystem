package com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload;

import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.personal.CustomerAnniversaryTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.personal.IdNoTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.personal.NameTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.swagger.CifNoTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.swagger.CustomerTypeTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.swagger.IdTypeTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.swagger.InvolvedPartyNoTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.enums.InvolvedPartyRoleType;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.enums.InvolvedPartyStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "查詢關係人(Involved Party)資訊Response Payload", description = "查詢關係人(Involved Party)資訊Response Payload")
public class QueryInvolvedPartyByConditionV2Rs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "關係人資訊列表", description = "關係人資訊列表")
    @JsonProperty("involvedParties")
    private List<QueryInvolvedPartyItemV2Rs> involvedParties;

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(title = "查詢關係人(Involved Party)單筆資訊Response Payload", description = "查詢關係人(Involved Party)單筆資訊Response Payload")
    public static
    class QueryInvolvedPartyItemV2Rs implements Serializable {
        private static final long serialVersionUID = 1L;

        @Comment("物件唯一識別碼@N")
        @Column(name = "ID", length = 28)
        @JsonProperty("id")
        private BigInteger id;

        @Schema(title = InvolvedPartyNoTemplate.TITLE, description = InvolvedPartyNoTemplate.DESCRIPTION, example = InvolvedPartyNoTemplate.EXAMPLE)
        @JsonProperty(InvolvedPartyNoTemplate.JSON_PROPERTY)
        private BigInteger involvedPartyNo;

        @Schema(title = "關係人類型", description = "關係人類型: 01:客戶 02:會員 03:訪客", example = "01")
        @JsonProperty("roleType")
        private InvolvedPartyRoleType roleType;

        @Schema(title = "狀態", description = "狀態: 01:生效 02:失效 03:未知", example = "01")
        @JsonProperty("status")
        private InvolvedPartyStatus status;

        @Schema(title = CifNoTemplate.TITLE, description = CifNoTemplate.DESCRIPTION, example = CifNoTemplate.EXAMPLE)
        @JsonProperty(CifNoTemplate.JSON_PROPERTY)
        private String cifNo;

        @Schema(title = IdNoTemplate.TITLE, description = IdNoTemplate.DESCRIPTION, example = IdNoTemplate.EXAMPLE)
        @JsonProperty(IdNoTemplate.JSON_PROPERTY)
        private String idNo;

        @Schema(title = CustomerAnniversaryTemplate.TITLE, description = CustomerAnniversaryTemplate.DESCRIPTION, example = CustomerAnniversaryTemplate.EXAMPLE)
        @JsonProperty(CustomerAnniversaryTemplate.JSON_PROPERTY)
        private LocalDate customerAnniversary;

        @Schema(title = NameTemplate.TITLE, description = NameTemplate.DESCRIPTION, example = NameTemplate.EXAMPLE)
        @JsonProperty(NameTemplate.JSON_PROPERTY)
        private String name;

        @Schema(title = CustomerTypeTemplate.TITLE, description = CustomerTypeTemplate.DESCRIPTION, example = CustomerTypeTemplate.EXAMPLE)
        @JsonProperty(CustomerTypeTemplate.JSON_PROPERTY)
        private String customerType;

        @Schema(title = IdTypeTemplate.TITLE, description = IdTypeTemplate.DESCRIPTION, example = IdTypeTemplate.EXAMPLE)
        @JsonProperty("idType")
        private String idType;
    }
}
