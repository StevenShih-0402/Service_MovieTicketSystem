package com.ctbcbank.navi.mid.campaign.management.functional;

import com.ctbcbank.navi.mid.campaign.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignCreateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.CampaignUpdateRq;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.create.CampaignCreateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.create.CreateCampaignServiceImpl;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.update.CampaignUpdateRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.update.CampaignUpdateServiceImpl;
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

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = CampaignManagementApplication.class)
public class S22 {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCampaignServiceImpl createCampaignServiceImpl;
    @MockBean
    private CampaignUpdateServiceImpl campaignUpdateServiceImpl;

    private static final String campaignCreate = "/v1/campaign/create";
    private static final String campaignUpdate = "/v1/campaign/update";
    private static final String campaignUpdateStatus = "/v1/campaign/update/status";

    @Test
    @Order(1)
    @DisplayName("AC1-1")
//    經辦進行行銷優惠活動設定
    public void ac1_1() throws Exception {
        Mockito.when(createCampaignServiceImpl.create(any())).thenReturn(CampaignCreateRsBo.builder().build());
        CampaignCreateRq campaignCreateRq = generateCampaignCreateRq();
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(campaignCreate, campaignCreateRq)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("AC1-2")
//    經辦進行行銷優惠活動調整
    public void ac1_2() throws Exception {
        Mockito.when(campaignUpdateServiceImpl.update(any())).thenReturn(CampaignUpdateRsBo.builder().build());
        CampaignUpdateRq campaignUpdateRq = generateCampaignUpdateRq();
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(campaignUpdate, campaignUpdateRq)).andDo(print())
                .andExpect(status().isOk());
    }

    public CampaignCreateRq generateCampaignCreateRq() {
        CampaignCreateRq campaignCreateRq = new CampaignCreateRq();
        CampaignCreateRq.CampaignInfo campaignInfo = new CampaignCreateRq.CampaignInfo();
        campaignInfo.setName("測試活動");
        campaignInfo.setDescription("測試活動");
        campaignCreateRq.setCampaignInfo(campaignInfo);
        return campaignCreateRq;
    }

    public CampaignUpdateRq generateCampaignUpdateRq() {
        CampaignUpdateRq campaignUpdateRq = new CampaignUpdateRq();
        CampaignUpdateRq.CampaignInfo campaignInfo = new CampaignUpdateRq.CampaignInfo();
        campaignInfo.setId(BigInteger.valueOf(1L));
        campaignInfo.setName("編輯活動");
        campaignInfo.setDescription("編輯活動");
        campaignUpdateRq.setCampaignInfo(campaignInfo);
        return campaignUpdateRq;
    }

}
