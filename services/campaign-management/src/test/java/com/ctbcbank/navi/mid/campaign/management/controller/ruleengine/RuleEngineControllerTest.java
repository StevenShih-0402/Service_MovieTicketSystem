package com.ctbcbank.navi.mid.campaign.management.controller.ruleengine;

import com.ctbcbank.navi.mid.campaign.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.campaign.management.controller.permission.payload.PermissionQueryByUserRq;
import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineBasicInformation;
import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineNonSynchronousRq;
import com.ctbcbank.navi.mid.campaign.management.controller.ruleengine.payload.QueryRuleEngineSynchronousRq;
import com.ctbcbank.navi.mid.campaign.management.service.permission.querybyuser.PermissionQueryByUserRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querynonsynchronous.QueryRuleEngineNonSynchronousRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querynonsynchronous.QueryRuleEngineNonSynchronousServiceImpl;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querysynchronous.QueryRuleEngineSynchronousRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.ruleengine.querysynchronous.QueryRuleEngineSynchronousServiceImpl;
import com.ibm.cbmp.fabric.test.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = CampaignManagementApplication.class)
class RuleEngineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QueryRuleEngineSynchronousServiceImpl queryRuleEngineSynchronousServiceImpl;

    @MockBean
    private QueryRuleEngineNonSynchronousServiceImpl queryRuleEngineNonSynchronousServiceImpl;

    private static final String querySynchronous = "/v1/rule-engine/query/synchronous";
    private static final String queryNonSynchronous = "/v1/rule-engine/query/non-synchronous";

    @Test
    @Order(1)
    @DisplayName("RuleEngineController.querySynchronous()_success")
    public void querySynchronous_success() throws Exception {
        when(queryRuleEngineSynchronousServiceImpl.query(any())).thenReturn(QueryRuleEngineSynchronousRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(querySynchronous, QueryRuleEngineSynchronousRq.builder()
                        .queryRuleEngineBasicInformation(new QueryRuleEngineBasicInformation())
                        .build())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("RuleEngineController.querySynchronous()_failed")
    public void querySynchronous_failed() throws Exception {
        when(queryRuleEngineSynchronousServiceImpl.query(any())).thenReturn(QueryRuleEngineSynchronousRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(querySynchronous, QueryRuleEngineSynchronousRq.builder()
                        .queryRuleEngineBasicInformation(new QueryRuleEngineBasicInformation())
                        .build())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @DisplayName("RuleEngineController.queryNonSynchronous()_success")
    public void queryNonSynchronous_success() throws Exception {
        when(queryRuleEngineNonSynchronousServiceImpl.query(any())).thenReturn(QueryRuleEngineNonSynchronousRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(queryNonSynchronous, QueryRuleEngineNonSynchronousRq.builder()
                        .queryRuleEngineBasicInformation(new QueryRuleEngineBasicInformation()).build())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("RuleEngineController.queryNonSynchronous()_failed")
    public void queryNonSynchronous_failed() throws Exception {
        when(queryRuleEngineNonSynchronousServiceImpl.query(any())).thenReturn(QueryRuleEngineNonSynchronousRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(queryNonSynchronous, QueryRuleEngineNonSynchronousRq.builder()
                        .queryRuleEngineBasicInformation(new QueryRuleEngineBasicInformation()).build())).andDo(print())
                .andExpect(status().isOk());
    }


}