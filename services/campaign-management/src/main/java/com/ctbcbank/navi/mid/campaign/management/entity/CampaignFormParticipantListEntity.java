package com.ctbcbank.navi.mid.campaign.management.entity;

import com.ibm.cbmp.fabric.foundation.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "TB_CAMPAIGN_FORM_PARTICIPANT_LIST")
public class CampaignFormParticipantListEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CAMPAIGN_FORM_NO", nullable = false)
    private String campaignFormNo;

    @Column(name = "CAMPAIGN_NO", nullable = false)
    private String campaignNo;

    @Column(name = "ID_NO", nullable = true)
    private String idNo;

    @Column(name = "CIF_NO", nullable = true)
    private String cifNo;

    @Column(name = "IP_NO", nullable = false)
    private BigInteger ipNo;
}
