package com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCampaignCustomerListDetailConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String customerListNo;
    private List<CampaignCustomerListStatusEnum> statusList;
}
