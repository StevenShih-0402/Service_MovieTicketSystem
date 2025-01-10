package com.ctbcbank.navi.mid.campaign.management.entity;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignParticipantTypeEnum;
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
@Table(name = "TB_CAMPAIGN")
public class CampaignEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CAMPAIGN_NO", nullable = false)
    private String campaignNo;

    @Column(name = "START_DATE_TIME", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "END_DATE_TIME", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "CATEGORY", nullable = true)
    private String category;

    @Column(name = "IS_IMMEDIATE", nullable = true)
    private Boolean isImmediate;

    @Column(name = "IMMEDIATE_TRANSACTION_CODE", nullable = true)
    private String immediateTransactionCode;

    @Column(name = "IS_LISTING", nullable = false)
    private Boolean isListing;

    @Column(name = "CREATE_EMPLOYEE_NO", nullable = false)
    private String createEmployeeNo;

    @Column(name = "IS_PARTICIPANT_LIST", nullable = false)
    private Boolean isParticipantList;

    @Column(name = "CUSTOMER_LIST_NO", nullable = true)
    private String customerListNo;

    @Column(name = "PARTICIPANT_LIST_VERSION", nullable = true)
    private String participantListVersion;

    @Column(name = "PARTICIPANT_TYPE", nullable = true)
    @Enumerated(EnumType.STRING)
    private CampaignParticipantTypeEnum participantType;

    @Column(name = "PARTICIPANT_LIST_LIMIT", nullable = true)
    private BigInteger participantListLimit;

}
