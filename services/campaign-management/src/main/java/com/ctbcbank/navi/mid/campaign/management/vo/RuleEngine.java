package com.ctbcbank.navi.mid.campaign.management.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class RuleEngine {
    private BigInteger campaignId;
    private RuleGrope root;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
