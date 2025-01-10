package com.ctbcbank.navi.mid.campaign.management.service.customerlist.query;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListQueryRqBo {
    private List<CampaignCustomerListStatusEnum> statusList;
}
