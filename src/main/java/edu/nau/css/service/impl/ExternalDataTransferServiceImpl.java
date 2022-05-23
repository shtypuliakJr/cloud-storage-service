package edu.nau.css.service.impl;

import edu.nau.css.aws.AwsS3Service;
import edu.nau.css.domain.ObjectEntity;
import edu.nau.css.domain.ObjectMetaInfo;
import edu.nau.css.domain.User;
import edu.nau.css.service.ExternalDataTransferService;
import edu.nau.css.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ExternalDataTransferServiceImpl implements ExternalDataTransferService {

    @Autowired
    private AwsS3Service awsS3Service;

    @Autowired
    private MetaDataService metaDataService;

    @Override
    public ObjectEntity downloadObject(String key) {

        ResponseBytes<GetObjectResponse> responseBytes = awsS3Service.getObject(key);
        InputStreamResource inputStreamResource = new InputStreamResource(responseBytes.asInputStream());
        GetObjectResponse response = responseBytes.response();
        return ObjectEntity.builder()
                .filename(response.metadata().get("file-name"))
                .fileType(response.contentType())
                .length(response.contentLength())
                .meta(response.metadata())
                .inputStreamResource(inputStreamResource)
                .build();
    }

    @Override
    public StreamingResponseBody downloadMultipleObjects(List<String> keys, User user, OutputStream responseOutputStream) {

        return outputStream -> {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(responseOutputStream)) {
                for (String key : keys) {
                    String formattedKey = String.format("%s/%s", user.getName(), key);
                    ObjectEntity objectEntity = downloadObject(formattedKey);

                    Resource inputStreamResource = objectEntity.getInputStreamResource();
                    ZipEntry zipEntry = new ZipEntry(key);
                    zipEntry.setSize(objectEntity.getLength());
                    zipEntry.setTime(System.currentTimeMillis());

                    zipOutputStream.putNextEntry(zipEntry);

                    StreamUtils.copy(inputStreamResource.getInputStream(), zipOutputStream);
                    zipOutputStream.closeEntry();
                }
                zipOutputStream.finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public List<ObjectMetaInfo> uploadObject(MultipartFile[] files, String path, User user) {

        List<ObjectMetaInfo> objectMetaInfoList = new java.util.ArrayList<>(files.length);

        for (MultipartFile file : files) {

            long size = file.getSize();

            String keyDbParentObject = path;
            String keyDB = path.equals("/") ? file.getOriginalFilename() : String.format("%s/%s", path.substring(1), file.getOriginalFilename());
            String keyS3 = path.equals("/")
                    ? String.format("%s/%s", user.getName(), file.getOriginalFilename())
                    : String.format("%s/%s/%s", user.getName(), keyDbParentObject, file.getOriginalFilename());

            String fileType = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().indexOf(".") + 1);

            ObjectMetaInfo parentObjectMetaInfo = metaDataService.getObjectMetaInfo(keyDbParentObject, user);

            try (InputStream inputStream = file.getResource().getInputStream()) {
                PutObjectResponse putObjectResponse = awsS3Service.saveObject(keyS3, inputStream, size);
                objectMetaInfoList.add(metaDataService.saveObject(keyDB, parentObjectMetaInfo, fileType, user));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return objectMetaInfoList;
    }

}
