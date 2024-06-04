package com.proyectointegrador.msevents.service.implement;

import com.proyectointegrador.msevents.service.interfaces.IAwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class AwsService implements IAwsService {

    private static final Logger LOGGER = Logger.getLogger(AwsService.class.getName());

//    private AmazonS3 amazonS3;

    @Value("${server.aws.s3.bucket}")
    private String bucketName;

    @Override
    public List<String> uploadFiles(List<MultipartFile> files) throws Exception {
        return List.of();
    }

    @Override
    public List<String> getObjectsFromS3() {
        return List.of();
    }

    @Override
    public InputStream downloadFile(String key) {
        return null;
    }

    @Override
    public List<String> generateImageUrls(List<String> fileName) {
        return List.of();
    }
}
