package com.ctbcbank.navi.mid.campaign.management.entity;

import com.ctbcbank.navi.mid.campaign.management.enums.ReviewStatusEnum;
import com.ibm.cbmp.fabric.foundation.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "TB_CAMPAIGN_FORM_COMMENT")
public class CampaignFormCommentEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CAMPAIGN_FORM_NO", nullable = false)
    private String campaignFormNo;

    @Column(name = "CAMPAIGN_NO", nullable = false)
    private String campaignNo;

    @Column(name = "CAMPAIGN_FORM_COMMENT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewStatusEnum campaignFormCommentType;

    @Column(name = "\"COMMENT\"", nullable = true)
    private String comment;

    @Column(name = "CREATE_EMPLOYEE_NO", nullable = false)
    private String createEmployeeNo;

}
