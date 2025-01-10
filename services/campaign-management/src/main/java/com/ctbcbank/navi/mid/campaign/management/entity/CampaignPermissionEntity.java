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

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@DynamicUpdate
@Table(name = "TB_CAMPAIGN_PERMISSION")
public class CampaignPermissionEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "NAME", nullable = true)
    private String name;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;
}
