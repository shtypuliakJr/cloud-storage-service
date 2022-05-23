package edu.nau.css.aws.worker.impl;

import edu.nau.css.configuration.aws.AwsConfigParam;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.util.concurrent.CompletableFuture;

public class S3RenameWorker {

    private final S3AsyncClient s3Client;
    private final String bucket = AwsConfigParam.BUCKET.get();

    public S3RenameWorker(S3AsyncClient s3Client) {
        this.s3Client = s3Client;
    }

    public void renameObject(String oldKey, String newKey) {

        CopyObjectRequest copyRequest = CopyObjectRequest.builder()
                .sourceBucket(bucket)
                .sourceKey(oldKey)
                .destinationBucket(bucket)
                .destinationKey(newKey)
                .build();
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(oldKey)
                .build();

        CompletableFuture<CopyObjectResponse> copyFutureResponse = s3Client.copyObject(copyRequest);

        s3Client.copyObject(copyRequest)
                .thenApply((copyObjectResponse) -> s3Client.deleteObject(deleteRequest));
    }
}
