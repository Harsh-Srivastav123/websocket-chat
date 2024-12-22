package com.websocketchat.websocketchat.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocketchat.websocketchat.model.AppSecrets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class SecretsConfig {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.secrets.database-secret}")
    private String databaseSecret;

    private AppSecrets appSecrets;

    @PostConstruct
    public void init() {
        try {
            // Create a Secrets Manager client
            AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .build();

            GetSecretValueRequest request = new GetSecretValueRequest()
                    .withSecretId(databaseSecret);

            GetSecretValueResult result = client.getSecretValue(request);
            String secret = result.getSecretString();

            // Parse the secret JSON
            ObjectMapper objectMapper = new ObjectMapper();
            appSecrets = objectMapper.readValue(secret, AppSecrets.class);
            log.info(appSecrets.toString());
        } catch (Exception e) {
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