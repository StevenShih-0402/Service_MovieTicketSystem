package com.ctbcbank.navi.mid.campaign.management.service.customerlisdetail.querybycustomerlistno;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
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
public class CustomerListDetailQueryByCustomerListNoRsBo {
    private Long totalElements;
    private Integer totalPages;
    private Integer number;
    private Integer size;
    private List<CustomerListDetailInfoBo> customerListDetailInfoList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerListDetailInfoBo {
        private BigInteger customerListDetailId;
        private String customerListNo;
        private CampaignCustomerListStatusEnum status;
        private String idNo;
        private String name;
        private List<BigInteger> ipNoList;
        private String message;
        private Boolean isSingleIpNo;
        private BigInteger chosenIpNo;
        private LocalDateTime createDttm;
        private LocalDateTime updateDttm;
    }
}
