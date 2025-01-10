package com.ctbcbank.navi.mid.campaign.management.repository;

import com.ctbcbank.navi.mid.campaign.management.entity.CampaignPermissionEntity;
import com.ibm.cbmp.fabric.foundation.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CampaignPermissionRepository extends BaseRepository<CampaignPermissionEntity, BigInteger> {

    @Query(
            value = """
                    SELECT * FROM TB_CAMPAIGN_PERMISSION WHERE ID IN (
                        SELECT PERMISSION_ID FROM TB_CAMPAIGN_ROLE_PERMISSION WHERE ROLE_ID = :roleId
                    )
                    """, nativeQuery = true
    )
    List<CampaignPermissionEntity> queryPermissionByRoleId(BigInteger roleId);

    @Query(
            value = """
                    SELECT * FROM TB_CAMPAIGN_PERMISSION WHERE ID IN (
                        SELECT PERMISSION_ID FROM TB_CAMPAIGN_ROLE_PERMISSION WHERE ROLE_ID = 
                            (
                                SELECT ROLE_ID FROM TB_CAMPAIGN_USER_PROFILE WHERE EMPLOYEE_NO = :employeeNo AND ROWNUM = 1
                            )
                    )
                    """, nativeQuery = true
    )
    List<CampaignPermissionEntity> queryPermissionByEmployeeNo(String employeeNo);
}
