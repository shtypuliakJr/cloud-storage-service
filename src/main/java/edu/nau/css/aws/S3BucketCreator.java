package edu.nau.css.aws;

import edu.nau.css.aws.exception.BucketExistsException;
import edu.nau.css.configuration.aws.AwsConfigParam;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class S3BucketCreator {

    private final S3Client s3Client;

    public S3BucketCreator(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void configS3Bucket() {

        try {
            checkProjectS3Bucket();


            CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                    .bucket(AwsConfigParam.BUCKET.get())
                    .createBucketConfiguration(CreateBucketConfiguration.builder()
                            .locationConstraint(BucketLocationConstraint.US_EAST_2).build())
                    .build();

            s3Client.createBucket(bucketRequest);

            PutBucketVersioningRequest versioningRequest = PutBucketVersioningRequest.builder()
                    .bucket(AwsConfigParam.BUCKET.get())
                    .versioningConfiguration(VersioningConfiguration.builder()
                            .status(BucketVersioningStatus.ENABLED)
                            .build())
                    .build();

            // smth wrong with request
//            s3Client.putBucketVersioning(versioningRequest);

        } catch (BucketExistsException | ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
        }

    }

    private void checkProjectS3Bucket() throws BucketExistsException, ExecutionException, InterruptedException {
        List<String> buckets = s3Client.listBuckets().buckets().stream()
                .map(Bucket::name)
                .filter(bucketFromList -> bucketFromList.equals(AwsConfigParam.BUCKET.get()))
                .collect(Collectors.toList());

        if (buckets.contains(AwsConfigParam.BUCKET.get())) throw new BucketExistsException();
    }

}
