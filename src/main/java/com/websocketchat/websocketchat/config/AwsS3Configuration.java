package com.websocketchat.websocketchat.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

@Configuration
public class AwsS3Configuration {


    @Value("${aws.region}")
    private String region;

    @Bean
    public AmazonS3 getConfiguration() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                // Use default credential provider chain which includes IAM roles
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }
}
