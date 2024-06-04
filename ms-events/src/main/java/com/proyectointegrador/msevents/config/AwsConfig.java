package com.proyectointegrador.msevents.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
    @Value("${server.aws.access_key_id}")
    private String awsAccessKey;

    @Value("${server.aws.secret_access_key}")
    private String awsSecretKey;

    @Value("${server.aws.s3.region}")
    private String awsRegion;

    
}
