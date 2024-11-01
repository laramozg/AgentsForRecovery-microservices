package org.example.sportsuser;

import org.example.sportsuser.configurations.security.AuthenticationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(AuthenticationProperties.class)
public class SportsUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsUserApplication.class, args);
    }

}
