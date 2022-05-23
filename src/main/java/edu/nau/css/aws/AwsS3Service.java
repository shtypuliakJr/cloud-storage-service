package edu.nau.css.aws;

import edu.nau.css.aws.worker.impl.S3GetWorker;
import edu.nau.css.aws.worker.impl.S3SaveWorker;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.InputStream;

public class AwsS3Service {

    @Autowired
    private final S3Client s3Client;

    public AwsS3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }


    public ResponseBytes<GetObjectResponse> getObject(String key) {
        S3GetWorker s3GetWorker = new S3GetWorker(s3Client);
        return s3GetWorker.download(key);
    }

    public PutObjectResponse saveObject(String key, InputStream inputStream, Long contentLength) {
        S3SaveWorker s3SaveWorker = new S3SaveWorker(s3Client);
        return s3SaveWorker.execute(key, inputStream, contentLength);
    }

}
