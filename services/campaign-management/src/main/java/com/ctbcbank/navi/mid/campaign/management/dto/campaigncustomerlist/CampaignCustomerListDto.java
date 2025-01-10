package com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlist;

import com.ctbcbank.navi.mid.campaign.management.dto.BaseDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignCustomerListDto extends BaseDto {
    private String customerListNo;
    private CampaignCustomerListStatusEnum status;
    private String createEmployeeNo;
    private BigInteger totalCount;
    private String name;
}
