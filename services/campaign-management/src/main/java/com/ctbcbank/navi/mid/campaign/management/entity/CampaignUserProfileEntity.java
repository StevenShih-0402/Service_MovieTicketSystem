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
@Table(name = "TB_CAMPAIGN_USER_PROFILE")
public class CampaignUserProfileEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "EMPLOYEE_NO", nullable = false)
    private String employeeNo;

    @Column(name = "NAME", nullable = true)
    private String name;

    @Column(name = "NAME_ENGLISH", nullable = true)
    private String nameEnglish;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "ROLE_ID", nullable = false)
    private BigInteger roleId;

    @Column(name = "TOKEN", nullable = false)
    private String token;

}
