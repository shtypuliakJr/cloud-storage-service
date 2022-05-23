package edu.nau.css.aws.worker.impl;

import edu.nau.css.aws.exception.ObjectDoesNotExistException;
import edu.nau.css.aws.worker.S3Worker;
import edu.nau.css.configuration.aws.AwsConfigParam;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class S3DeleteWorker implements S3Worker {

    private final S3AsyncClient s3Client;
    private final String bucket = AwsConfigParam.BUCKET.get();

    public S3DeleteWorker(S3AsyncClient s3Client) {
        this.s3Client = s3Client;
    }

    public List<Pair<String, CompletableFuture<DeleteObjectResponse>>> delete(String key) throws ObjectDoesNotExistException {

        List<Pair<String, CompletableFuture<DeleteObjectResponse>>> futureResponses = new ArrayList<>();

        try {
            List<S3Object> contents = s3Client
                    .listObjects(ListObjectsRequest.builder().bucket(bucket).prefix(key).build()).get().contents();
            if (!contents.isEmpty()) {
                for (S3Object object : contents) {
                    DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                            .key(object.key())
                            .bucket(bucket)
                            .build();
                    futureResponses.add(Pair.of(object.key(), s3Client.deleteObject(deleteObjectRequest)));
                }
            }
            return futureResponses;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        throw new ObjectDoesNotExistException(String.format("Object does not exist by key = %s", key));
    }

}