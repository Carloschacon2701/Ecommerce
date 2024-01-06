package com.ecommerce.ecommerce.AWS;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


@Service
@RequiredArgsConstructor
public class S3Service {


    private final S3Client s3Client;

    @Value("${application.security.s3.bucketName}")
    private String bucketName;

    public void uploadFile(String key, byte[] file){
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(objectRequest, RequestBody.fromBytes(file));

    }

    public byte[] getFile( String keyName) {
        try{
            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(keyName)
                    .bucket(bucketName)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);

            return objectBytes.asByteArray();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }


}
