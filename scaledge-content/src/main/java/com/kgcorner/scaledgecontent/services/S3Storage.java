package com.kgcorner.scaledgecontent.services;

/*
Description : <Write class Description>
Author: kumar
Created on : 4/6/19
*/

import com.kgcorner.scaledge.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Properties;

@Service
public class S3Storage implements ContentStorage{


    @Value("${aws.s3.bucket}")
    private String bucketName;

    private String accessKey;
    private String accessSecret;

    private S3Client s3Client;

    public S3Storage() {
        Properties properties = ConfigurationBuilder.getInstance()
                .loadConfigurationFile(S3Storage.class.getResourceAsStream(("/aws.properties")));
        accessKey = properties.getProperty("aws.access.key");
        accessSecret = properties.getProperty("aws.secret.key");

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, accessSecret);
        AwsCredentialsProvider provider = StaticCredentialsProvider.create(awsCredentials);
        s3Client = S3Client.builder().region(Region.US_EAST_1).credentialsProvider(provider).build();
    }

    @Override
    public String storeDocument(MultipartFile file, String name) throws IOException {
        if(file.isEmpty())
            throw new IllegalArgumentException("Empty file");
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(name).build();
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        return name;
    }

    @Override
    public String storeDocument(MultipartFile file) throws IOException {
        return storeDocument(file, file.getOriginalFilename());
    }


}