package com.ctbcbank.navi.mid.movie.management;

import com.ibm.cbmp.fabric.foundation.CbmpFabricFoundationConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Log4j2
@Import(CbmpFabricFoundationConfiguration.class)
@OpenAPIDefinition(
        info = @Info(
                title = "電影購票管理",
                description = "電影購票管理微服務。",
                summary = "MovieOrder Management"
        )
)
public class CampaignManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampaignManagementApplication.class, args);
    }
}
