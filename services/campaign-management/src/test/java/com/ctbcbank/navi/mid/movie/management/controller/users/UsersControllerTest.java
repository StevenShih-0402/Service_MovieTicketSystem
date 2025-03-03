package com.ctbcbank.navi.mid.movie.management.controller.users;

import com.ctbcbank.navi.mid.movie.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.movie.management.controller.users.payload.UserCreateRq;
import com.ctbcbank.navi.mid.movie.management.controller.users.payload.UserDeleteRq;
import com.ctbcbank.navi.mid.movie.management.controller.users.payload.UserQueryRq;
import com.ctbcbank.navi.mid.movie.management.controller.users.payload.UserUpdateRq;
import com.ctbcbank.navi.mid.movie.management.service.movies.create.MovieCreateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.users.create.UserCreateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.users.create.UsersCreateServiceImpl;
import com.ctbcbank.navi.mid.movie.management.service.users.delete.UsersDeleteServiceImpl;
import com.ctbcbank.navi.mid.movie.management.service.users.query.UserQueryRsBo;
import com.ctbcbank.navi.mid.movie.management.service.users.query.UsersQueryServiceImpl;
import com.ctbcbank.navi.mid.movie.management.service.users.update.UserUpdateRsBo;
import com.ctbcbank.navi.mid.movie.management.service.users.update.UsersUpdateServiceImpl;
import com.ibm.cbmp.fabric.foundation.enums.FabricResponseCode;
import com.ibm.cbmp.fabric.foundation.exception.NaviException;
import com.ibm.cbmp.fabric.test.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = CampaignManagementApplication.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersCreateServiceImpl usersCreateServiceImpl;
    @MockBean
    private UsersQueryServiceImpl usersQueryServiceImpl;
    @MockBean
    private UsersUpdateServiceImpl usersUpdateServiceImpl;
    @MockBean
    private UsersDeleteServiceImpl usersDeleteServiceImpl;

    private static final String create = "/v1/users/create";
    private static final String query = "/v1/users/query-by-id";
    private static final String update = "/v1/users/update";
    private static final String delete = "/v1/users/delete";

    @Test
    @Order(1)
    @DisplayName("UsersController.create()_success")
    public void create_success() throws Exception {

        UserCreateRsBo userCreateRsBo = UserCreateRsBo.builder()
                .id(BigInteger.valueOf(1))
                .userName("StringUser123")
                .build();

        when(usersCreateServiceImpl.create(any())).thenReturn(userCreateRsBo);

        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(create, UserCreateRq.builder()
                .userName("StringUser123")
                .email("stringUSERTest@gmail.com")
                .build())
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.userName").exists());

    }

    @Test
    @Order(2)
    @DisplayName("UsersController.create()_failed")
    public void create_failed() throws Exception {
        when(usersCreateServiceImpl.create(any())).thenThrow(new NaviException(FabricResponseCode.DATA_DUPLICATE, "信箱不能重複。"));
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(create, UserCreateRq.builder()
                .userName("stringUser")
                .email("stringUSERTest@gmail.com")
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @DisplayName("UsersController.query()_success")
    public void query_success() throws Exception {
        when(usersQueryServiceImpl.query(any())).thenReturn(UserQueryRsBo.builder()
                .usersInfoList(List.of(
                        UserQueryRsBo.UsersInfoBo.builder()
                                .userName("StringUser")
                                .email("stringUSERTest@gmail.com")
                                .build()
                ))
                .build());

        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(query, UserQueryRq.builder()
                .id(BigInteger.valueOf(15))
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("UsersController.query()_failed")
    public void query_failed() throws Exception {
        when(usersQueryServiceImpl.query(any())).thenThrow(new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。"));
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(query, UserQueryRq.builder()
                .id(BigInteger.valueOf(100))
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    @DisplayName("UsersController.update()_success")
    public void update_success() throws Exception {
        when(usersUpdateServiceImpl.update(any())).thenReturn(UserUpdateRsBo.builder()
                        .usersInfoBo(
                        UserUpdateRsBo.UsersInfoBo.builder()
                                .userName("StringUser")
                                .email("stringUSERTest@gmail.com")
                                .build()
                )
                .build());

        mockMvc.perform(TestUtils.putMockMvcRequestBuilders(update, UserUpdateRq.builder()
                .id(BigInteger.valueOf(3))
                .usersInfo(
                        UserUpdateRq.UsersInfo.builder()
                                .userName("StringUser")
                                .email("stringUSERTest@gmail.com")
                                .build()
                )
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @DisplayName("UsersController.update()_failed")
    public void update_failed() throws Exception {
        when(usersUpdateServiceImpl.update(any())).thenThrow(new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。"));
        mockMvc.perform(TestUtils.putMockMvcRequestBuilders(update, UserUpdateRq.builder()
                .id(BigInteger.valueOf(3))
                .usersInfo(
                        UserUpdateRq.UsersInfo.builder()
                                .userName("StringUser")
                                .email("stringUSERTest@gmail.com")
                                .build()
                )
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(7)
    @DisplayName("UsersController.delete()_success")
    public void delete_success() throws Exception {
        doNothing().when(usersDeleteServiceImpl).delete(any());

        mockMvc.perform(TestUtils.deleteMockMvcRequestBuilders(delete, UserDeleteRq.builder()
                        .id(BigInteger.valueOf(3))
                        .build())
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));;
    }

    @Test
    @Order(8)
    @DisplayName("UsersController.delete()_failed")
    public void delete_failed() throws Exception {
        doThrow(new NaviException(FabricResponseCode.DATA_NOT_FOUND, "找不到對應的 ID。")).when(usersDeleteServiceImpl).delete(any());
        mockMvc.perform(TestUtils.deleteMockMvcRequestBuilders(delete, UserDeleteRq.builder()
                .id(BigInteger.valueOf(30))
                .build())
        ).andDo(print()).andExpect(status().isOk());
    }
}
