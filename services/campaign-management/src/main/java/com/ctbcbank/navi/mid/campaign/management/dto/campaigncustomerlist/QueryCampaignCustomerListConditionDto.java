package com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlist;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCampaignCustomerListConditionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CampaignCustomerListStatusEnum> statusList;
}
