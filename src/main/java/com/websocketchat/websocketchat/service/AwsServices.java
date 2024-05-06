package com.websocketchat.websocketchat.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.UUID;

@Service
public class AwsServices {

    @Autowired
    AmazonS3 s3Client;

    @Value("${aws.bucket}")
    private String bucketName;

    public String uploadMedia(MultipartFile file ,String fileName){
        try {
//            String fileName=file.getName()+ UUID.randomUUID().toString();
            s3Client.putObject(bucketName, fileName, file.getInputStream(), null);
            return s3Client.getUrl(bucketName,fileName).toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public byte[] downloadFile(String fileName ){
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName,fileName);

        S3Object s3Object = s3Client.getObject(getObjectRequest);
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        try {

            return  IOUtils.toByteArray(objectInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
