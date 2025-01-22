package com.ctbcbank.navi.mid.campaign.management.functional;

import com.ctbcbank.navi.mid.campaign.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.QueryRuleEngineTreeByConditionRequest;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.querybyrule.CampaignQueryByRuleRq;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybyrule.CampaignQueryByRuleRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.querybyrule.CampaignQueryByRuleServiceImpl;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.update.CampaignFormUpdateRsBo;
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
public class S26 {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignQueryByRuleServiceImpl campaignQueryByRuleServiceImpl;

    private static final String campaignQueryByRule = "/v1/campaign/query/by-rule";

    /**
     * User Story: OOO.客戶優惠_2 _PM查詢歷史優惠行銷活動, Acceptance Criteria: 1, Given: PM進行查詢同類型歷史行銷活動
     *
     * @throws Exception
     */
    @Test
    @Order(1)
    @DisplayName("AC1-1")
    public void ac1_1() throws Exception {
        Mockito.when(campaignQueryByRuleServiceImpl.queryByRule(any())).thenReturn(CampaignQueryByRuleRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(campaignQueryByRule, generateCampaignQueryByRuleRq())).andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * User Story: OOO.客戶優惠_2 _PM查詢歷史優惠行銷活動, Acceptance Criteria: 2, Given: PM進行查詢同類型歷史行銷活動
     *
     * @throws Exception
     */
    @Test
    @Order(2)
    @DisplayName("AC1-2")
    public void ac1_2() throws Exception {
        Mockito.when(campaignQueryByRuleServiceImpl.queryByRule(any())).thenReturn(CampaignQueryByRuleRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(campaignQueryByRule, generateCampaignQueryByRuleRq())).andDo(print())
                .andExpect(status().isOk());
    }

    public CampaignQueryByRuleRq generateCampaignQueryByRuleRq() {
        CampaignQueryByRuleRq campaignQueryByRuleRq = new CampaignQueryByRuleRq();
        campaignQueryByRuleRq.setCampaignName("測試");
        return campaignQueryByRuleRq;
    }

}
