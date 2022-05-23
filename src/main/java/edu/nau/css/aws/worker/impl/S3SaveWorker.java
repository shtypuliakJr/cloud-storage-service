package edu.nau.css.aws.worker.impl;

import edu.nau.css.aws.worker.S3Worker;
import edu.nau.css.configuration.aws.AwsConfigParam;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.InputStream;
import java.util.Map;

public class S3SaveWorker implements S3Worker {

    private final S3Client s3Client;

    private final String bucket = AwsConfigParam.BUCKET.get();

    public S3SaveWorker(S3Client s3Client) {
        this.s3Client = s3Client;
    }


    public PutObjectResponse execute(String key, InputStream inputStream, Long contentLength) {

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .metadata(Map.of("x-amz-meta-file-name", key.substring(key.lastIndexOf("/") + 1)))
                .build();

        return s3Client.putObject(request, RequestBody.fromInputStream(inputStream, contentLength));
    }

}