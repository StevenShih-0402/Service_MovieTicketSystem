package com.ctbcbank.navi.mid.campaign.management.functional;

import com.ctbcbank.navi.mid.campaign.management.CampaignManagementApplication;
import com.ctbcbank.navi.mid.campaign.management.controller.campaign.payload.addparticipantlist.CampaignAddParticipantListRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.create.CampaignFormCreateRq;
import com.ctbcbank.navi.mid.campaign.management.controller.campaignform.payload.update.CampaignFormUpdateRq;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist.CampaignAddParticipantListRsBo;
import com.ctbcbank.navi.mid.campaign.management.service.campaign.addparticipantlist.CampaignAddParticipantListServiceImpl;
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

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = CampaignManagementApplication.class)
public class S24 {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignFormUpdateServiceImpl campaignFormUpdateServiceImpl;

    @MockBean
    private CampaignAddParticipantListServiceImpl campaignAddParticipantListServiceImpl;

    private static final String campaignFormUpdate = "/v1/campaign-form/update";
    private static final String campaignAddParticipantList = "/v1/campaign/add/participant-list";

    /**
     * User Story: OOO.客戶優惠_1依名單參加活動且可參加抽獎, Acceptance Criteria: 1, Given: 經辦設定撥薪客戶為可取得跨行轉帳優惠免手續費之客戶
     *
     * @throws Exception
     */
    @Test
    @Order(1)
    @DisplayName("AC1-1")
    public void ac1_1() throws Exception {
        CampaignFormUpdateRq campaignFormUpdateRq = generateCampaignFormUpdateRq();
        Mockito.when(campaignFormUpdateServiceImpl.update(any())).thenReturn(CampaignFormUpdateRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(campaignFormUpdate, campaignFormUpdateRq)).andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * User Story: OOO.客戶優惠_1依名單參加活動且可參加抽獎, Acceptance Criteria: 2, Given: 經辦上傳特定名單客戶，為可直接獲得跨行轉帳優惠免手續費之客戶
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
     * User Story: OOO.客戶優惠_1依名單參加活動且可參加抽獎, Acceptance Criteria: 3, Given: 客戶已符合獲得跨行轉帳手續費優惠券
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

    /**
     * User Story: OOO.客戶優惠_2點選參加行銷活動, Acceptance Criteria: 1, Given: 經辦設定客戶需點選才能參加跨行轉帳優惠手續費行銷活動
     *
     * @throws Exception
     */
    @Test
    @Order(4)
    @DisplayName("AC2-1")
    public void ac2_1() throws Exception {
        CampaignAddParticipantListRq campaignAddParticipantListRq = new CampaignAddParticipantListRq();
        campaignAddParticipantListRq.setCampaignNo("0bf1b628-a3ed-4a20-bdcb-c7f192930b3b");
        campaignAddParticipantListRq.setIpNo(BigInteger.ONE);
        Mockito.when(campaignAddParticipantListServiceImpl.addParticipantList(any())).thenReturn(CampaignAddParticipantListRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(campaignAddParticipantList, campaignAddParticipantListRq)).andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * User Story: OOO.客戶優惠_2點選參加行銷活動, Acceptance Criteria: 2, Given: 經辦設定客戶需點選才能參加跨行轉帳優惠手續費行銷活動
     *
     * @throws Exception
     */
    @Test
    @Order(5)
    @DisplayName("AC2-2")
    public void ac2_2() throws Exception {
        CampaignAddParticipantListRq campaignAddParticipantListRq = new CampaignAddParticipantListRq();
        campaignAddParticipantListRq.setCampaignNo("0bf1b628-a3ed-4a20-bdcb-c7f192930b3b");
        campaignAddParticipantListRq.setIpNo(BigInteger.ONE);
        Mockito.when(campaignAddParticipantListServiceImpl.addParticipantList(any())).thenReturn(CampaignAddParticipantListRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(campaignAddParticipantList, campaignAddParticipantListRq)).andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * User Story: OOO.客戶優惠_2點選參加行銷活動, Acceptance Criteria: 3, Given: 經辦設定客戶需點選才能參加跨行轉帳優惠手續費行銷活動
     *
     * @throws Exception
     */
    @Test
    @Order(6)
    @DisplayName("AC2-3")
    public void ac2_3() throws Exception {
        CampaignAddParticipantListRq campaignAddParticipantListRq = new CampaignAddParticipantListRq();
        campaignAddParticipantListRq.setCampaignNo("0bf1b628-a3ed-4a20-bdcb-c7f192930b3b");
        campaignAddParticipantListRq.setIpNo(BigInteger.ONE);
        Mockito.when(campaignAddParticipantListServiceImpl.addParticipantList(any())).thenReturn(CampaignAddParticipantListRsBo.builder().build());
        mockMvc.perform(TestUtils.postMockMvcRequestBuilders(campaignAddParticipantList, campaignAddParticipantListRq)).andDo(print())
                .andExpect(status().isOk());
    }

    public CampaignFormUpdateRq generateCampaignFormUpdateRq() {
        CampaignFormUpdateRq campaignFormUpdateRq = new CampaignFormUpdateRq();
        CampaignFormUpdateRq.CampaignFormInfo campaignFormInfo = new CampaignFormUpdateRq.CampaignFormInfo();
        campaignFormInfo.setName("更新活動客戶名單");
        campaignFormUpdateRq.setCampaignFormInfo(campaignFormInfo);
        return campaignFormUpdateRq;
    }
}
