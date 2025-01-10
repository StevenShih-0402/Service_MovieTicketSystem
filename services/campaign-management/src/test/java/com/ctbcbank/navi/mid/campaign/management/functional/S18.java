package com.ctbcbank.navi.mid.campaign.management.functional;

import com.ctbcbank.navi.mid.campaign.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignQueryRq;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.query.CampaignQueryRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.query.CampaignQueryServiceImpl;
import com.ibm.cbmp.fabric.test.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = CampaignManagementApplication.class)
public class S18 {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignQueryServiceImpl campaignQueryServiceImpl;

    private static final String query = "/v1/campaign/query";

    @Test
    @Order(1)
    @DisplayName("AC1-1")
//    查詢活動清單
    public void ac1_1() throws Exception {
        Mockito.when(campaignQueryServiceImpl.query(any())).thenReturn(CampaignQueryRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(query, CampaignQueryRq.builder().build())).andDo(print())
                .andExpect(status().isOk());
    }
}
