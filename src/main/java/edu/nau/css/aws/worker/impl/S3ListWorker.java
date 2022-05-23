package edu.nau.css.aws.worker.impl;

import edu.nau.css.aws.worker.S3Worker;
import edu.nau.css.configuration.aws.AwsConfigParam;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

public class S3ListWorker implements S3Worker {

    private final S3Client s3Client;

    private final String bucket = AwsConfigParam.BUCKET.get();

    public S3ListWorker(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public ListObjectsResponse execute(String prefix) {

        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                .bucket(bucket)
                .prefix(prefix)
                .build();

        return s3Client.listObjects(listObjectsRequest);
    }

}
