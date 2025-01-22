package com.ctbcbank.navi.mid.campaign.management.controller.customerlist;

import com.ctbcbank.navi.mid.campaign.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryRq;
import com.ctbcbank.navi.mid.campaign.management.controller.customerlist.payload.create.CustomerListCreateRq;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.query.CampaignQueryRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.create.CustomerListCreateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.customerlist.create.CustomerListCreateServiceImpl;
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
public class CustomerListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerListCreateServiceImpl customerListCreateServiceImpl;

    private static final String create = "/v1/customer-list/create";

    @Test
    @Order(1)
    @DisplayName("CustomerListController.create()_success")
    public void create_success() throws Exception {
        when(customerListCreateServiceImpl.create(any())).thenReturn(CustomerListCreateRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(create, CustomerListCreateRq.builder().build())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("CustomerListController.create()_failed")
    public void create_failed() throws Exception {
        when(customerListCreateServiceImpl.create(any())).thenReturn(CustomerListCreateRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(create, CustomerListCreateRq.builder().build())).andDo(print())
                .andExpect(status().isOk());
    }

}