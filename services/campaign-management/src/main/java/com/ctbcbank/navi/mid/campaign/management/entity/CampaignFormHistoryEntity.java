package com.ctbcbank.navi.mid.campaign.management.entity;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignFormTypeEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import com.ibm.cbmp.fabric.foundation.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "TB_CAMPAIGN_FORM_HISTORY")
public class CampaignFormHistoryEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CAMPAIGN_FORM_NO", nullable = false)
    private String campaignFormNo;

    @Column(name = "CAMPAIGN_NO", nullable = false)
    private String campaignNo;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "CATEGORY", nullable = true)
    private String category;

    @Column(name = "IS_IMMEDIATE", nullable = false)
    private Boolean isImmediate;

    @Column(name = "IMMEDIATE_TRANSACTION_CODE", nullable = true)
    private String immediateTransactionCode;

    @Column(name = "START_DATE_TIME", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "END_DATE_TIME", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "REVIEW_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewStatusEnum reviewStatus;

    @Column(name = "CAMPAIGN_FORM_NO_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CampaignFormTypeEnum campaignFormType;

    @Column(name = "IS_LISTING", nullable = false)
    private Boolean isListing;

    @Column(name = "GROUP_NODE_DATA", nullable = true)
    private String groupNodeData;

    @Column(name = "CREATE_EMPLOYEE_NO", nullable = false)
    private String createEmployeeNo;

    @Column(name = "UPDATE_EMPLOYEE_NO", nullable = false)
    private String updateEmployeeNo;

}
