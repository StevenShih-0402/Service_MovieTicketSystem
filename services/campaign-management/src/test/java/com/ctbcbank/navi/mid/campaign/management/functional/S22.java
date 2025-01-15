package com.ctbcbank.navi.mid.campaign.management.functional;

import com.ctbcbank.navi.mid.campaign.management.CampaignManagementApplication;
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

    @Test
    @Order(1)
    @DisplayName("AC1-1")
//    經辦進行行銷優惠活動設定
    public void ac1_1() throws Exception {
    }

    @Test
    @Order(2)
    @DisplayName("AC1-2")
//    經辦進行行銷優惠活動調整
    public void ac1_2() throws Exception {
    }

}
