package com.ctbcbank.navi.mid.campaign.management.service.customerlisdetail.querybycustomerlistno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListDetailQueryByCustomerListNoRqBo {
    private String customerListNo;
    private Integer size;
    private Integer number;
}
