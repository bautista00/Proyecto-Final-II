package com.proyectointegrador.msevents.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${server.aws.access_key_id}")
    private String awsAccessKey;

    @Value("${server.aws.secret_access_key}")
    private String awsSecretKey;

    @Value("${server.aws.s3.region}")
    private String awsRegion;

    @Bean
    public AmazonS3 getS3Client(){
        BasicAWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(awsRegion))
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }
}
