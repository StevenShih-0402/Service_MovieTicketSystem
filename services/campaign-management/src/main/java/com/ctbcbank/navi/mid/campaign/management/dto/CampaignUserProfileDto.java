package com.ctbcbank.navi.mid.campaign.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignUserProfileDto extends BaseDto {
    private String employeeNo;
    private String name;
    private String nameEnglish;
    private String status;
    private BigInteger roleId;
    private String token;
}
