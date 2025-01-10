package com.ctbcbank.navi.mid.campaign.management.service.permission.querybyuser;

import com.ctbcbank.navi.mid.campaign.management.dao.CampaignPermissionDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignRoleDao;
import com.ctbcbank.navi.mid.campaign.management.dao.CampaignUserProfileDao;
import com.ctbcbank.navi.mid.campaign.management.dto.*;
import com.ctbcbank.navi.mid.campaign.management.enums.CampaignUserStatusEnum;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class PermissionQueryByUserServiceImpl implements PermissionQueryByUserService {

    private final String CLASS_NAME = PermissionQueryByUserServiceImpl.class.getSimpleName();
    private final CampaignUserProfileDao campaignUserProfileDao;
    private final CampaignPermissionDao campaignPermissionDao;
    private final CampaignRoleDao campaignRoleDao;


    @Override
    public PermissionQueryByUserRsBo queryByUser(String userToken) {


        // 查詢帳號
        QueryCampaignUserProfileConditionDto queryCampaignUserProfileConditionDto = new QueryCampaignUserProfileConditionDto();
        queryCampaignUserProfileConditionDto.setToken(userToken);
        queryCampaignUserProfileConditionDto.setStatus(CampaignUserStatusEnum.ACTIVATE.getCode());
        List<CampaignUserProfileDto> campaignUserProfileDtoList = campaignUserProfileDao.queryCampaignUserProfile(queryCampaignUserProfileConditionDto);
        log.info("[{}][queryByUser][campaignUserProfileDtoList: {}]", CLASS_NAME, campaignUserProfileDtoList);
        if (CollectionUtils.isEmpty(campaignUserProfileDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "user not found.");
        }
        CampaignUserProfileDto campaignUserProfileDto = campaignUserProfileDtoList.get(0);

        // 查詢角色
        BigInteger roleId = campaignUserProfileDto.getRoleId();
        QueryCampaignRoleConditionDto queryCampaignRoleConditionDto = new QueryCampaignRoleConditionDto();
        queryCampaignRoleConditionDto.setRoleId(roleId);
        List<CampaignRoleDto> campaignRoleDtoList = campaignRoleDao.queryCampaignRole(queryCampaignRoleConditionDto);
        log.info("[{}][queryByUser][campaignRoleDtoList: {}]", CLASS_NAME, campaignRoleDtoList);
        if (CollectionUtils.isEmpty(campaignRoleDtoList)) {
            throw new NaviException(FabricResponseCode.DATA_NOT_FOUND, "role not found.");
        }

        // 搜尋權限 By 角色ID
        List<CampaignPermissionDto> campaignPermissionDtoList = campaignPermissionDao.queryPermissionByRoleId(roleId);
        List<PermissionQueryByUserRsBo.PermissionBo> permissionBoList = campaignPermissionDtoList.stream().map(x -> {
            PermissionQueryByUserRsBo.PermissionBo permissionBo = new PermissionQueryByUserRsBo.PermissionBo();
            permissionBo.setId(x.getId());
            permissionBo.setCode(x.getCode());
            permissionBo.setName(x.getName());
            permissionBo.setDescription(x.getDescription());
            return permissionBo;
        }).toList();
        log.info("[{}][queryByUser][permissionBoList: {}]", CLASS_NAME, permissionBoList);

        PermissionQueryByUserRsBo permissionQueryByUserRsBo = new PermissionQueryByUserRsBo();
        List<PermissionQueryByUserRsBo.RoleBo> roleBoList = new ArrayList<>();
        CampaignRoleDto campaignRoleDto = campaignRoleDtoList.get(0);
        PermissionQueryByUserRsBo.RoleBo roleBo = new PermissionQueryByUserRsBo.RoleBo();
        roleBo.setId(campaignRoleDto.getId());
        roleBo.setName(campaignRoleDto.getName());
        roleBo.setDescription(campaignRoleDto.getDescription());
        roleBo.setStatus(campaignRoleDto.getStatus());
        roleBo.setType(campaignRoleDto.getType());
        roleBo.setPermissionList(getPermissionBoList(campaignPermissionDtoList));
        roleBoList.add(roleBo);
        permissionQueryByUserRsBo.setRoleList(roleBoList);
        return permissionQueryByUserRsBo;
    }

    private List<PermissionQueryByUserRsBo.PermissionBo> getPermissionBoList(List<CampaignPermissionDto> campaignPermissionDtoList) {
        List<PermissionQueryByUserRsBo.PermissionBo> permissionBoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(campaignPermissionDtoList)) {
            return permissionBoList;
        }

        permissionBoList = campaignPermissionDtoList.stream().map(x -> {
            PermissionQueryByUserRsBo.PermissionBo permissionBo = new PermissionQueryByUserRsBo.PermissionBo();
            permissionBo.setId(x.getId());
            permissionBo.setCode(x.getCode());
            permissionBo.setName(x.getName());
            permissionBo.setDescription(x.getDescription());
            return permissionBo;
        }).toList();

        return permissionBoList;
    }
}
