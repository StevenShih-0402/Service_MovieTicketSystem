package com.ctbcbank.navi.mid.campaign.management.service.permission.querybyuser;

import com.ctbcbank.navi.mid.campaign.management.controller.permission.payload.PermissionQueryByUserRs;
import com.ibm.cbmp.fabric.foundation.utils.CollectionUtils;
import com.ibm.cbmp.fabric.foundation.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class PermissionQueryByUserConverter {

    public static PermissionQueryByUserRs parseRsBoToRs(PermissionQueryByUserRsBo permissionQueryByUserRsBo) {
        PermissionQueryByUserRs permissionQueryByUserRs = new PermissionQueryByUserRs();
        permissionQueryByUserRs.setRoleList(getRoleList(permissionQueryByUserRsBo.getRoleList()));
        return permissionQueryByUserRs;
    }

    private static List<PermissionQueryByUserRs.Role> getRoleList(List<PermissionQueryByUserRsBo.RoleBo> roleBoList) {
        List<PermissionQueryByUserRs.Role> roleList = new ArrayList<>();
        if (CollectionUtils.isEmpty(roleBoList)) {
            return roleList;
        }
        roleList = roleBoList.stream().map(x -> {
            PermissionQueryByUserRs.Role role = new PermissionQueryByUserRs.Role();
            role.setId(x.getId());
            role.setName(x.getName());
            role.setDescription(x.getDescription());
            role.setStatus(x.getStatus());
            role.setType(x.getType());
            role.setPermissionList(getPermissionList(x.getPermissionList()));
            return role;
        }).toList();
        return roleList;
    }

    private static List<PermissionQueryByUserRs.Permission> getPermissionList(List<PermissionQueryByUserRsBo.PermissionBo> permissionBoList) {
        List<PermissionQueryByUserRs.Permission> permissionList = new ArrayList<>();
        if (ObjectUtils.isEmpty(permissionBoList) || CollectionUtils.isEmpty(permissionBoList)) {
            return permissionList;
        }

        permissionList = permissionBoList.stream().map(x -> {
            PermissionQueryByUserRs.Permission permission = new PermissionQueryByUserRs.Permission();
            permission.setId(x.getId());
            permission.setCode(x.getCode());
            permission.setName(x.getName());
            permission.setDescription(x.getDescription());
            return permission;
        }).toList();

        return permissionList;
    }
}
