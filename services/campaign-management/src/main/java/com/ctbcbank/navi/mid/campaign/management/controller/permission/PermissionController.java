package com.ctbcbank.navi.mid.campaign.management.controller.permission;

import com.ctbcbank.navi.mid.campaign.management.controller.permission.payload.PermissionQueryByUserRq;
import com.ctbcbank.navi.mid.campaign.management.controller.permission.payload.PermissionQueryByUserRs;
import com.ctbcbank.navi.mid.campaign.management.service.permission.querybyuser.PermissionQueryByUserConverter;
import com.ctbcbank.navi.mid.campaign.management.service.permission.querybyuser.PermissionQueryByUserRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.permission.querybyuser.PermissionQueryByUserService;
import com.ibm.cbmp.fabric.web.api.annotation.PostApiMapping;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/permission/", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController {
    private final PermissionQueryByUserService permissionQueryByUserService;

    @Operation(summary = "取得權限 By User", description = "取得權限 By User")
    @PostApiMapping("query/by-user")
    PermissionQueryByUserRs queryByUser(@Valid @RequestBody PermissionQueryByUserRq permissionQueryByUserRq) {
        PermissionQueryByUserRsBo permissionQueryByUserRsBo = permissionQueryByUserService.queryByUser(permissionQueryByUserRq.getUserToken());
        PermissionQueryByUserRs permissionQueryByUserRs = PermissionQueryByUserConverter.parseRsBoToRs(permissionQueryByUserRsBo);
        return permissionQueryByUserRs;
    }
}
