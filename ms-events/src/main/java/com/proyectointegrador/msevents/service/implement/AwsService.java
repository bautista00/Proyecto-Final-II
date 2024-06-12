package com.proyectointegrador.msevents.service.implement;

import com.proyectointegrador.msevents.service.interfaces.IAwsService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.net.URL;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AwsService implements IAwsService {

    private static final Logger LOGGER = Logger.getLogger(AwsService.class.getName());

    private final S3Client s3Client;

    @Value("${server.aws.s3.bucket}")
    private String bucketName;

    @Override
    public List<String> uploadFiles(List<MultipartFile> files) throws Exception {
        List<String> uploadedFiles = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String newFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                PutObjectRequest request = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(newFileName)
                        .build();
                s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
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
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();
        ListObjectsV2Response result = s3Client.listObjectsV2(listObjectsV2Request);
        List<S3Object> objects = result.contents();
        return objects.stream().map(S3Object::key).collect(Collectors.toList());
    }

    @Override
    public InputStream downloadFile(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        ResponseInputStream<GetObjectResponse> getObjectResponse = s3Client.getObject(getObjectRequest);
        return getObjectResponse == null ? null : getObjectResponse;
    }



    @Override
    public List<String> generateImageUrls(List<String> fileNames) {
        List<String> imageUrls = new ArrayList<>();
        for (String fileName : fileNames) {
            GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            URL url = s3Client.utilities().getUrl(getUrlRequest);
            imageUrls.add(url.toString());
        }
        return imageUrls;
    }
}
