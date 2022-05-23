package edu.nau.css.aws.worker.impl;

import edu.nau.css.aws.exception.CopyObjectInsideBucketException;
import edu.nau.css.aws.worker.S3Worker;
import edu.nau.css.configuration.aws.AwsConfigParam;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;

import java.util.concurrent.CompletableFuture;

public class S3MoveWorker implements S3Worker {

    private final S3AsyncClient s3Client;

    private final String bucket = AwsConfigParam.BUCKET.get();

    public S3MoveWorker(S3AsyncClient s3Client) {
        this.s3Client = s3Client;
    }

    public void moveObject(String fromKey, String toKey) {

        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .sourceBucket(bucket)
                .sourceKey(fromKey)
                .destinationBucket(bucket)
                .destinationKey(toKey)
                .build();
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(fromKey).build();

        CompletableFuture<CopyObjectResponse> completableFutureCopy = s3Client.copyObject(copyObjectRequest);
        completableFutureCopy.handle((response, ex) -> {
            if (ex != null)
                throw new CopyObjectInsideBucketException();
            return response;
        });

        CompletableFuture<DeleteObjectResponse> deleteObjectResponseCompletableFuture = s3Client.deleteObject(deleteObjectRequest);
        deleteObjectResponseCompletableFuture.handle((response, ex) -> {
            if (ex != null)
                throw new CopyObjectInsideBucketException();
            return response;
        });
    }

}
