package com.github.mohammadmasoomi.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//  TODO spring problems,
//  TODO spring application initializer,
//  TODO spring hateoas,
//  TODO Junit5 Test case, mockito
//  TODO use spring JWT Security
//  TODO method level log
//  TODO method actuator Prometheus Grafana

@SpringBootApplication(scanBasePackages = "com.github.mohammadmasoomi")
@EntityScan(basePackages = {"com.github.mohammadmasoomi.inventory.stock.entity", "com.github.mohammadmasoomi.inventory.core.entity"})
@EnableJpaRepositories({"com.github.mohammadmasoomi.inventory.stock.repository", "com.github.mohammadmasoomi.inventory.core.repository"})
@EnableTransactionManagement
@Configuration
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }


}
