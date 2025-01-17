package com.ctbcbank.navi.mid.campaign.management.functional;

import com.ctbcbank.navi.mid.campaign.management.CampaignManagementApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
@SpringBootTest(classes = CampaignManagementApplication.class)
public class S25 {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("AC1-1")
    public void ac1_1() throws Exception {
    }
}
