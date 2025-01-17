package com.ctbcbank.navi.mid.campaign.management.functional;

import com.ctbcbank.navi.mid.campaign.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.create.CampaignFormCreateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.update.CampaignFormUpdateRq;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.create.CampaignFormCreateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.create.CampaignFormCreateServiceImpl;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.update.CampaignFormUpdateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaignform.update.CampaignFormUpdateServiceImpl;
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
public class S23 {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignFormCreateServiceImpl campaignFormCreateServiceImpl;

    @MockBean
    private CampaignFormUpdateServiceImpl campaignFormUpdateServiceImpl;

    private static final String campaignFormCreate = "/v1/campaign-form/create";
    private static final String campaignFormUpdate = "/v1/campaign-form/update";

    /**
     * User Story: OOO.客戶優惠_1主管放行優惠活動, Acceptance Criteria: 1, Given: 經辦送出新增行銷優惠活動設定內容
     *
     * @throws Exception
     */
    @Test
    @Order(1)
    @DisplayName("AC1-1")
    public void ac1_1() throws Exception {
        CampaignFormCreateRq campaignFormCreateRq = generateCampaignFormCreateRq();
        Mockito.when(campaignFormCreateServiceImpl.create(any())).thenReturn(CampaignFormCreateRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(campaignFormCreate, campaignFormCreateRq)).andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * User Story: OOO.客戶優惠_1主管放行優惠活動, Acceptance Criteria: 2, Given: 經辦送出調整行銷優惠活動設定內容
     *
     * @throws Exception
     */
    @Test
    @Order(2)
    @DisplayName("AC1-2")
    public void ac1_2() throws Exception {
        CampaignFormUpdateRq campaignFormUpdateRq = generateCampaignFormUpdateRq();
        Mockito.when(campaignFormUpdateServiceImpl.update(any())).thenReturn(CampaignFormUpdateRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(campaignFormUpdate, campaignFormUpdateRq)).andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * User Story: OOO.客戶優惠_1主管放行優惠活動, Acceptance Criteria: 3, Given: 經辦送出申請下架行銷優惠活動設定內容
     *
     * @throws Exception
     */
    @Test
    @Order(3)
    @DisplayName("AC1-3")
    public void ac1_3() throws Exception {
        CampaignFormUpdateRq campaignFormUpdateRq = generateCampaignFormUpdateRq();
        Mockito.when(campaignFormUpdateServiceImpl.update(any())).thenReturn(CampaignFormUpdateRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(campaignFormUpdate, campaignFormUpdateRq)).andDo(print())
                .andExpect(status().isOk());
    }

    public CampaignFormCreateRq generateCampaignFormCreateRq() {
        CampaignFormCreateRq campaignFormCreateRq = new CampaignFormCreateRq();
        campaignFormCreateRq.setName("測試活動");
        return campaignFormCreateRq;
    }

    public CampaignFormUpdateRq generateCampaignFormUpdateRq() {
        CampaignFormUpdateRq campaignFormUpdateRq = new CampaignFormUpdateRq();
        CampaignFormUpdateRq.CampaignFormInfo campaignFormInfo = new CampaignFormUpdateRq.CampaignFormInfo();
        campaignFormInfo.setName("更新活動");
        campaignFormUpdateRq.setCampaignFormInfo(campaignFormInfo);
        return campaignFormUpdateRq;
    }

}
