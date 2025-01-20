package com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.payload;

import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.personal.CustomerAnniversaryTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.personal.IdNoTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.personal.NameTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.swagger.CifNoTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.swagger.IdTypeTemplate;
import com.ctbcbank.navi.mid.campaign.management.adapter.navi.custinfo.config.swagger.InvolvedPartyNoTemplate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "查詢關係人(Involved Party)資訊Request Payload", description = "查詢關係人(Involved Party)資訊Request Payload")
public class QueryInvolvedPartyByConditionV2Rq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = InvolvedPartyNoTemplate.TITLE, description = InvolvedPartyNoTemplate.DESCRIPTION, example = InvolvedPartyNoTemplate.EXAMPLE)
    @JsonProperty(InvolvedPartyNoTemplate.JSON_PROPERTY)
    private BigInteger involvedPartyNo;

    @Schema(title = IdNoTemplate.TITLE, description = IdNoTemplate.DESCRIPTION, nullable = false, minLength = 1, maxLength = 14, example = IdNoTemplate.EXAMPLE)
    @JsonProperty(value = IdNoTemplate.JSON_PROPERTY)
    private String identificationNo;

    @Schema(title = IdTypeTemplate.TITLE, description = IdTypeTemplate.DESCRIPTION, nullable = true, minLength = 1, maxLength = 4, example = IdTypeTemplate.EXAMPLE)
    @Length(max = 4, message = "客戶證件類型限制長度4")
    @JsonProperty(IdTypeTemplate.JSON_PROPERTY)
    private String customerIdType;

    @Schema(title = NameTemplate.TITLE, description = NameTemplate.DESCRIPTION, nullable = true, minLength = 1, maxLength = 30, example = NameTemplate.EXAMPLE)
    @JsonProperty(value = NameTemplate.JSON_PROPERTY)
    private String name;

    @Schema(title = CustomerAnniversaryTemplate.TITLE, description = CustomerAnniversaryTemplate.DESCRIPTION, nullable = true, example = CustomerAnniversaryTemplate.EXAMPLE)
    @JsonProperty(CustomerAnniversaryTemplate.JSON_PROPERTY)
    private LocalDate startDate;

    @Schema(title = CifNoTemplate.TITLE, description = CifNoTemplate.DESCRIPTION, nullable = true, minLength = 1, maxLength = 16, example = CifNoTemplate.EXAMPLE)
    @Length(max = 16, message = "銀行客戶編號限制長度16")
    @JsonProperty(value = CifNoTemplate.JSON_PROPERTY)
    private String cifNo;

    @Schema(title = "身分證字號/統編", description = "身分證字號/統編(前綴字模糊比對)", example = "A123456789")
    @JsonProperty("identificationNoStartsWith")
    private String identificationNoStartsWith;
}
