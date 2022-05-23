package edu.nau.css.aws;

import edu.nau.css.aws.worker.impl.S3CopyWorker;
import edu.nau.css.aws.worker.impl.S3DeleteWorker;
import edu.nau.css.aws.worker.impl.S3RenameWorker;
import edu.nau.css.dto.param.PairSourceDestinationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.utils.Pair;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AwsS3AsyncService {

    @Autowired
    private final S3AsyncClient s3Client;

    public AwsS3AsyncService(S3AsyncClient s3Client) {
        this.s3Client = s3Client;
    }

    public S3AsyncClient getS3AsyncClient() {
        return s3Client;
    }


    public void moveObjectsInsideBucket(List<String> keysFrom, String keyTo) {

    }

    public void copyObjectsInsideBucket(List<PairSourceDestinationDTO> sourceDestinationList) {

        S3CopyWorker worker = new S3CopyWorker(s3Client);

        for (PairSourceDestinationDTO keyValueDTO : sourceDestinationList) {
            String keyFrom = keyValueDTO.getKeySource();
            String keyTo = keyValueDTO.getKeyDestination();
            String objectName = keyFrom.substring(keyFrom.lastIndexOf("/"));

            CompletableFuture<CopyObjectResponse> completableFuture = worker.copyInsideBucket(keyFrom, keyTo + objectName);
            CopyObjectResponse copyObjectResponse = completableFuture.join();
            System.out.println(copyObjectResponse.copyObjectResult());
        }

    }

    public List<String> deleteObject(String formatKey) {
        S3DeleteWorker s3DeleteWorker = new S3DeleteWorker(s3Client);
        List<Pair<String, CompletableFuture<DeleteObjectResponse>>> delete = s3DeleteWorker.delete(formatKey);
        return delete.stream().map(Pair::left).collect(Collectors.toList());
    }

    public void renameObject(String keyS3, String newKeyS3) {
        S3RenameWorker s3RenameWorker = new S3RenameWorker(s3Client);
        s3RenameWorker.renameObject(keyS3, newKeyS3);
    }
}
