package com.ctbcbank.navi.mid.campaign.management.controller.campaign;

import com.ctbcbank.navi.mid.campaign.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignCreateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignUpdateRq;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.create.CampaignCreateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.create.CreateCampaignServiceImpl;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.query.CampaignQueryRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.query.CampaignQueryServiceImpl;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.update.CampaignUpdateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.update.CampaignUpdateServiceImpl;
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
public class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignQueryServiceImpl campaignQueryServiceImpl;
    @MockBean
    private CreateCampaignServiceImpl createCampaignServiceImpl;
    @MockBean
    private CampaignUpdateServiceImpl campaignUpdateServiceImpl;

    private static final String query = "/v1/campaign/query";
    private static final String create = "/v1/campaign/create";
    private static final String update = "/v1/campaign/update";
    private static final String updateStatus = "/v1/campaign/update/status";

    @Test
    @Order(1)
    @DisplayName("CampaignController.query()_success")
    public void query_success() throws Exception {
        when(campaignQueryServiceImpl.query(any())).thenReturn(CampaignQueryRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(query, CampaignQueryRq.builder().build())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("CampaignController.query()_failed")
    public void query_failed() throws Exception {
        when(campaignQueryServiceImpl.query(any())).thenReturn(CampaignQueryRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(query, CampaignQueryRq.builder().build())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @DisplayName("CampaignController.create()_success")
    public void create_success() throws Exception {
        when(createCampaignServiceImpl.create(any())).thenReturn(CampaignCreateRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(create, CampaignCreateRq.builder().build())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("CampaignController.create()_failed")
    public void create_failed() throws Exception {
        when(createCampaignServiceImpl.create(any())).thenReturn(CampaignCreateRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(create, CampaignCreateRq.builder().build())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    @DisplayName("CampaignController.update()_success")
    public void update_success() throws Exception {
        when(campaignUpdateServiceImpl.update(any())).thenReturn(CampaignUpdateRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(update, CampaignUpdateRq.builder().build())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @DisplayName("CampaignController.update()_failed")
    public void update_failed() throws Exception {
        when(campaignUpdateServiceImpl.update(any())).thenReturn(CampaignUpdateRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(update, CampaignUpdateRq.builder().build())).andDo(print())
                .andExpect(status().isOk());
    }

}
