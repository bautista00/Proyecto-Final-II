package com.proyectointegrador.msevents.service.implement;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.proyectointegrador.msevents.service.interfaces.IAwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AwsService implements IAwsService {

    private static final Logger LOGGER = Logger.getLogger(AwsService.class.getName());

    private AmazonS3 amazonS3;

    @Value("${server.aws.s3.bucket}")
    private String bucketName;

    @Override
    public List<String> uploadFiles(List<MultipartFile> files) throws Exception {
        List<String> uploadedFiles = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String newFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                PutObjectRequest request = new PutObjectRequest(bucketName, newFileName, file.getInputStream(), null);
                amazonS3.putObject(request);
                LOGGER.info("File uploaded with the name..." + newFileName);
                uploadedFiles.add(newFileName);
            }
            return uploadedFiles;
        } catch (IOException e) {
            LOGGER.info("Error uploading file");
        }
        throw new Exception("The file was not uploaded correctly");
    }


    @Override
    public List<String> getObjectsFromS3() {
        ListObjectsV2Result result = amazonS3.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        return objects.stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }

    @Override
    public InputStream downloadFile(String key) {
        S3Object object = amazonS3.getObject(bucketName, key);
        return object.getObjectContent();
    }

    @Override
    public List<String> generateImageUrls(List<String> fileNames) {
        List<String> imageUrls = new ArrayList<>();
        for (String fileName : fileNames) {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
            Date expiration = new Date();
            long expirationMillis = expiration.getTime() + (7L * 24 * 60 * 60 * 1000);
            expiration.setTime(expirationMillis);
            generatePresignedUrlRequest.setExpiration(expiration);

            ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides()
                    .withCacheControl("No-cache")
                    .withContentDisposition("attachment; filename=" + fileName);
            generatePresignedUrlRequest.setResponseHeaders(responseHeaders);

            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
            imageUrls.add(url.toString());
        }
        return imageUrls;
    }
}
