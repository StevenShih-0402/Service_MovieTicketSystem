package com.ctbcbank.navi.mid.campaign.management.service.campaign.querybyrule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignQueryByRuleRsBo {
    private Long totalElements;
    private Integer totalPages;
    private Integer number;
    private Integer size;
    private List<CampaignBo> campaignList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignBo {
        private BigInteger id;
        private String campaignNo;
        private Boolean isListing;
        private String category;
        private String name;
        private String description;
        private Boolean isImmediate;
        private String immediateTransactionCode;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private String createEmployeeNo;
        private LocalDateTime createDttm;
        private LocalDateTime updateDttm;
    }
}
