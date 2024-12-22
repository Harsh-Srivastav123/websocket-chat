package com.websocketchat.websocketchat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocketchat.websocketchat.model.AppSecrets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;
import jakarta.annotation.PostConstruct;

@Slf4j
@Configuration
public class SecretsConfig {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.secrets.database-secret}")
    private String secretName;

    private AppSecrets appSecrets;

    @PostConstruct
    public void init() {
        try {
            SecretsManagerClient client = SecretsManagerClient.builder()
                    .region(Region.of(region))
                    .build();

            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            GetSecretValueResponse response = client.getSecretValue(request);
            String secret = response.secretString();

            ObjectMapper objectMapper = new ObjectMapper();
            appSecrets = objectMapper.readValue(secret, AppSecrets.class);
            log.info("Successfully loaded database secrets");
        } catch (Exception e) {
            log.error("Error fetching secrets: ", e);
            throw new RuntimeException("Error fetching secrets", e);
        }
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(appSecrets.getDbUrl())
                .username(appSecrets.getDbUsername())
                .password(appSecrets.getDbPassword())
                .build();
    }
}