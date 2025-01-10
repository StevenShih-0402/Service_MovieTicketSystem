package com.ctbcbank.navi.mid;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.ctbcbank.navi.mid"},
        entityManagerFactoryRef = "midEntityManagerFactory",
        transactionManagerRef = "midTransactionManager"
)
public class MidJpaConfig {
}
