package edu.nau.css.aws.worker.impl;

import edu.nau.css.aws.worker.S3Worker;
import edu.nau.css.configuration.aws.AwsConfigParam;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public class S3GetWorker implements S3Worker {

    private final S3Client s3Client;

    private final String bucket = AwsConfigParam.BUCKET.get();

    public S3GetWorker(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public ResponseBytes<GetObjectResponse> download(String key) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        return s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes());
    }

}