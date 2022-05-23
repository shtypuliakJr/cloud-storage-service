package edu.nau.css.configuration.aws;

import edu.nau.css.aws.AwsS3AsyncService;
import edu.nau.css.aws.AwsS3Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

    @Bean
    public AwsS3AsyncService awsS3AsyncService() {
        return new AwsS3AsyncService(s3AsyncClient());
    }

    @Bean
    public AwsS3Service awsS3Service() {
        return new AwsS3Service(s3Client());
    }

    @Bean
    public S3AsyncClient s3AsyncClient() {

        return S3AsyncClient.builder()
                .region(Region.of(AwsConfigParam.AWS_REGION.get()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    public S3Client s3Client() {

        return S3Client.builder()
                .region(Region.of(AwsConfigParam.AWS_REGION.get()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

}
