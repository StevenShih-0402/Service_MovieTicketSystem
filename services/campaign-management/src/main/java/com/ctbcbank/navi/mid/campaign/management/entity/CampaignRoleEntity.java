package com.ctbcbank.navi.mid.campaign.management.entity;

import com.ctbcbank.navi.mid.campaign.management.enums.CampaignRoleStatusEnum;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignRoleTypeEnum;
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
@Table(name = "TB_CAMPAIGN_ROLE")
public class CampaignRoleEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME", nullable = true)
    private String name;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private CampaignRoleStatusEnum status;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CampaignRoleTypeEnum type;
}
