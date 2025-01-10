package com.ctbcbank.navi.mid.campaign.management.entity;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignCustomerListStatusEnum;
import com.ibm.cbmp.fabric.foundation.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "TB_CAMPAIGN_CUSTOMER_LIST")
public class CampaignCustomerListEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CUSTOMER_LIST_NO", nullable = false)
    private String customerListNo;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private CampaignCustomerListStatusEnum status;

    @Column(name = "CREATE_EMPLOYEE_NO", nullable = false)
    private String createEmployeeNo;

    @Column(name = "TOTAL_COUNT", nullable = false)
    private BigInteger totalCount;

    @Column(name = "NAME", nullable = false)
    private String name;

}
