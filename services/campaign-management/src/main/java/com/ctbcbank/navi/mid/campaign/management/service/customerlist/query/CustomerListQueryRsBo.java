package com.ctbcbank.navi.mid.campaign.management.service.customerlist.query;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListQueryRsBo {
    private List<CustomerListInfoBo> customerListInfoList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerListInfoBo {
        private String customerListNo;
        private String name;
        private CampaignCustomerListStatusEnum status;
        private String createEmployeeNo;
        private BigInteger totalCount;
    }
}
