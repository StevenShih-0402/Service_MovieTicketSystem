package com.ctbcbank.navi.mid.campaign.management.entity;

import com.ctbcbank.navi.mid.campaign.management.entity.converter.InvolvedPartyNoListConverter;
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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "TB_CAMPAIGN_CUSTOMER_LIST_DETAIL")
public class CampaignCustomerListDetailEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CUSTOMER_LIST_NO", nullable = false)
    private String customerListNo;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private CampaignCustomerListStatusEnum status;

    @Column(name = "ID_NO", nullable = false)
    private String idNo;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "IP_NO_LIST", nullable = false)
    @Convert(converter = InvolvedPartyNoListConverter.class)
    private List<BigInteger> ipNoList;

    @Column(name = "MESSAGE", nullable = true)
    private String message;

    @Column(name = "IS_SINGLE_IP_NO", nullable = false)
    private Boolean isSingleIpNo;

    @Column(name = "CHOSEN_IP_NO", nullable = true)
    private BigInteger chosenIpNo;

}
