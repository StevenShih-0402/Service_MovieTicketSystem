package com.ctbcbank.navi.mid.movie.management.controller.users;

import com.ctbcbank.navi.mid.movie.management.controller.users.payload.*;
import com.ctbcbank.navi.mid.movie.management.service.users.create.UsersCreateConverter;
import com.ctbcbank.navi.mid.movie.management.service.users.create.UserCreateRqBo;
import com.ctbcbank.navi.mid.movie.management.service.users.create.UserCreateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.users.create.UsersCreateService;
import com.ctbcbank.navi.mid.movie.management.service.users.delete.UserDeleteRqBo;
import com.ctbcbank.navi.mid.movie.management.service.users.delete.UsersDeleteConverter;
import com.ctbcbank.navi.mid.movie.management.service.users.delete.UsersDeleteService;
import com.ctbcbank.navi.mid.movie.management.service.users.query.UsersQueryConverter;
import com.ctbcbank.navi.mid.movie.management.service.users.query.UserQueryRqBo;
import com.ctbcbank.navi.mid.movie.management.service.users.query.UserQueryRsBo;
import com.ctbcbank.navi.mid.movie.management.service.users.query.UsersQueryService;
import com.ctbcbank.navi.mid.movie.management.service.users.update.UsersUpdateConverter;
import com.ctbcbank.navi.mid.movie.management.service.users.update.UserUpdateRqBo;
import com.ctbcbank.navi.mid.movie.management.service.users.update.UserUpdateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.users.update.UsersUpdateService;
import com.ibm.cbmp.fabric.web.api.annotation.DeleteApiMapping;
import com.ibm.cbmp.fabric.web.api.annotation.PostApiMapping;
import com.ibm.cbmp.fabric.web.api.annotation.PutApiMapping;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/users/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {

    private final UsersCreateService usersCreateService;
    private final UsersQueryService usersQueryService;
    private final UsersUpdateService usersUpdateService;
    private final UsersDeleteService usersDeleteService;

    @Operation(summary = "新增用戶", description = "新增用戶")
    @PostApiMapping(value = "create")
    UserCreateRs create(@Valid @RequestBody UserCreateRq usersCreateRq) {
        UserCreateRqBo userCreateRqBo = UsersCreateConverter.parseToBo(usersCreateRq);
        UserCreateRsBo userCreateRsBo = usersCreateService.create(userCreateRqBo);
        return UsersCreateConverter.parseToRs(userCreateRsBo);
    }

    @Operation(summary = "透過 ID 查詢用戶", description = "透過 ID 查詢用戶")
    @PostApiMapping(value = "query-by-id")
    UserQueryRs create(@Valid @RequestBody UserQueryRq userQueryRq) {
        UserQueryRqBo userQueryRqBo = UsersQueryConverter.parseToBo(userQueryRq);
        UserQueryRsBo userQueryRsBo = usersQueryService.query(userQueryRqBo);
        return UsersQueryConverter.parseToRs(userQueryRsBo);
    }

    @Operation(summary = "更新用戶", description = "更新用戶")
    @PutApiMapping(value = "update")
    UserUpdateRs update(@Valid @RequestBody UserUpdateRq userUpdateRq) {
        UserUpdateRqBo userUpdateRqBo = UsersUpdateConverter.parseToBo(userUpdateRq);
        UserUpdateRsBo userUpdateRsBo = usersUpdateService.update(userUpdateRqBo);
        return UsersUpdateConverter.parseToRs(userUpdateRsBo);
    }

    @Operation(summary = "刪除用戶", description = "刪除用戶")
    @DeleteApiMapping(value = "delete")
    ApiResponsePayload delete(@Valid @RequestBody UserDeleteRq moviesDeleteRq) {
        UserDeleteRqBo movieDeleteRqBo = UsersDeleteConverter.parseToBo(moviesDeleteRq);
        usersDeleteService.delete(movieDeleteRqBo);
        return new ApiResponsePayload();
    }
}
