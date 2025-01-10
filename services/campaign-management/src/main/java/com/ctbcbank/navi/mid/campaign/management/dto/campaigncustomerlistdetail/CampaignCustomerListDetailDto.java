package com.ctbcbank.navi.mid.campaign.management.dto.campaigncustomerlistdetail;

import com.ctbcbank.navi.mid.campaign.management.dto.BaseDto;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignCustomerListDetailDto extends BaseDto {
    private String customerListNo;
    private CampaignCustomerListStatusEnum status;
    private String idNo;
    private String name;
    private List<BigInteger> ipNoList;
    private String message;
    private Boolean isSingleIpNo;
    private BigInteger chosenIpNo;

}
