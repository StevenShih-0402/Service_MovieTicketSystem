package com.ctbcbank.navi.mid.campaign.management.dao;

import com.ctbcbank.navi.mid.campaign.management.dto.CampaignPermissionDto;
import com.ctbcbank.navi.mid.campaign.management.entity.CampaignPermissionEntity;
import com.ctbcbank.navi.mid.campaign.management.repository.CampaignPermissionRepository;
import com.ibm.cbmp.fabric.foundation.utils.BeanUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;
import com.ibm.cbmp.fabric.foundation.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class CampaignPermissionDao {

    private final CampaignPermissionRepository campaignPermissionRepository;

    @Transactional(readOnly = true)
    public List<CampaignPermissionDto> queryPermissionByRoleId(BigInteger roleId) {
        List<CampaignPermissionDto> reDataList = new ArrayList<>();
        if (ObjectUtils.isEmpty(roleId)) {
            return reDataList;
        }
        List<CampaignPermissionEntity> CampaignPermissionEntities = campaignPermissionRepository.queryPermissionByRoleId(roleId);
        ListUtils.emptyIfNull(CampaignPermissionEntities).forEach(entity -> {
            CampaignPermissionDto dto = new CampaignPermissionDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

    @Transactional(readOnly = true)
    public List<CampaignPermissionDto> queryPermissionByEmployeeNo(String employeeNo) {
        List<CampaignPermissionDto> reDataList = new ArrayList<>();
        if (StringUtils.isBlank(employeeNo)) {
            return reDataList;
        }
        List<CampaignPermissionEntity> CampaignPermissionEntities = campaignPermissionRepository.queryPermissionByEmployeeNo(employeeNo);
        ListUtils.emptyIfNull(CampaignPermissionEntities).forEach(entity -> {
            CampaignPermissionDto dto = new CampaignPermissionDto();
            BeanUtils.copyProperties(entity, dto);
            reDataList.add(dto);
        });
        return reDataList;
    }

}
