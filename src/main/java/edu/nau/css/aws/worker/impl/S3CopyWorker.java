package edu.nau.css.aws.worker.impl;

import edu.nau.css.aws.exception.CopyObjectInsideBucketException;
import edu.nau.css.aws.worker.S3Worker;
import edu.nau.css.configuration.aws.AwsConfigParam;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;

import java.util.concurrent.CompletableFuture;

public class S3CopyWorker implements S3Worker {

    private final S3AsyncClient s3Client;

    private final String bucket = AwsConfigParam.BUCKET.get();

    public S3CopyWorker(S3AsyncClient s3Client) {
        this.s3Client = s3Client;
    }

    public CompletableFuture<CopyObjectResponse> copyInsideBucket(final String fromKey, final String toKey) {

        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .sourceBucket(bucket)
                .sourceKey(fromKey)
                .destinationBucket(bucket)
                .destinationKey(toKey)
                .build();

        CompletableFuture<CopyObjectResponse> completableFuture = s3Client.copyObject(copyObjectRequest);
        completableFuture.handle((response, ex) -> {
            if (ex != null)
                throw new CopyObjectInsideBucketException();
            return response;
        });
        return completableFuture;
    }

}
