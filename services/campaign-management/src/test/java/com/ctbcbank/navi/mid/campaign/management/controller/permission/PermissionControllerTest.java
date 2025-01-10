package com.ctbcbank.navi.mid.campaign.management.controller.permission;

import com.ctbcbank.navi.mid.campaign.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.campaign.management.controller.permission.payload.PermissionQueryByUserRq;
import com.ctbcbank.navi.mid.campaign.management.service.permission.querybyuser.PermissionQueryByUserRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.permission.querybyuser.PermissionQueryByUserServiceImpl;
import com.ibm.cbmp.fabric.test.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = CampaignManagementApplication.class)
class PermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermissionQueryByUserServiceImpl permissionQueryByUserServiceImpl;

    private static final String queryByUser = "/v1/permission/query/by-user";


    @Test
    @Order(1)
    @DisplayName("PermissionController.queryByUser()_success")
    public void queryByUser_success() throws Exception {
        when(permissionQueryByUserServiceImpl.queryByUser(any())).thenReturn(PermissionQueryByUserRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(queryByUser, PermissionQueryByUserRq.builder().build())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("PermissionController.queryByUser()_failed")
    public void queryByUser_failed() throws Exception {
        when(permissionQueryByUserServiceImpl.queryByUser(any())).thenReturn(PermissionQueryByUserRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(queryByUser, PermissionQueryByUserRq.builder().build())).andDo(print())
                .andExpect(status().isOk());
    }


}