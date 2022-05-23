package edu.nau.css.service.impl;

import edu.nau.css.aws.AwsS3AsyncService;
import edu.nau.css.domain.ObjectMetaInfo;
import edu.nau.css.domain.User;
import edu.nau.css.dto.param.PairSourceDestinationDTO;
import edu.nau.css.exception.RootRenameRejectionException;
import edu.nau.css.service.InternalDataTransferService;
import edu.nau.css.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternalDataTransferServiceImpl implements InternalDataTransferService {

    private final AwsS3AsyncService awsS3Service;
    private final MetaDataService metaDataService;

    @Autowired
    public InternalDataTransferServiceImpl(AwsS3AsyncService awsS3Service, MetaDataService metaDataService) {
        this.awsS3Service = awsS3Service;
        this.metaDataService = metaDataService;
    }

    @Override
    public void moveObjectsInside(List<PairSourceDestinationDTO> sourceDestinationList) {

    }

    @Override
    public void copyObjectsInside(List<PairSourceDestinationDTO> sourceDestinationList, User user) {

        List<String> sourceKeys = sourceDestinationList.stream()
                .map(PairSourceDestinationDTO::getKeySource)
                .collect(Collectors.toList());
        metaDataService.checkObjectsExistence(sourceKeys, user);


        List<String> destinationKeys = sourceDestinationList.stream()
                .map(PairSourceDestinationDTO::getKeyDestination)
                .collect(Collectors.toList());
        metaDataService.checkObjectsExistenceAndIsFolder(destinationKeys, user);

        List<PairSourceDestinationDTO> collect = sourceDestinationList.stream()
                .map(pair -> {
                    String pathDestinationFormatted = pair.getKeyDestination().equals("/")
                            ? user.getName()
                            : String.format("%s/%s", user.getName(), pair.getKeyDestination());
                    String pathSourceFormatted = String.format("%s/%s", user.getName(), pair.getKeySource());
                    return new PairSourceDestinationDTO(pathSourceFormatted, pathDestinationFormatted);
                })
                .collect(Collectors.toList());

        awsS3Service.copyObjectsInsideBucket(collect);
        List<ObjectMetaInfo> objectsMetaInfo = metaDataService.getObjectsMetaInfo(sourceKeys, user);

        for (PairSourceDestinationDTO pair : sourceDestinationList) {
            ObjectMetaInfo objectMetaInfo = metaDataService.getObjectMetaInfo(pair.getKeySource(), user);
            ObjectMetaInfo newObjectMetaInfo = ObjectMetaInfo.builder()
                    .withObjectName(pair.getKeyDestination().equals("/")
                            ? pair.getKeySource().substring(pair.getKeySource().lastIndexOf("/") + 1)
                            : String.format("%s/%s", pair.getKeyDestination(), pair.getKeySource().substring(pair.getKeySource().lastIndexOf("/") + 1)))
                    .withParentObject(metaDataService.getObjectMetaInfo(pair.getKeyDestination(), user))
                    .withIsFolder(objectMetaInfo.getIsFolder())
                    .withCreatedAt(LocalDateTime.now())
                    .withUpdatedAt(LocalDateTime.now())
                    .withUser(user)
                    .build();
            metaDataService.saveObject(newObjectMetaInfo);
        }
    }

    @Override
    public List<String> deleteObject(String key, User user) {

        metaDataService.checkObjectExistence(key, user);
        String formatKey = String.format("%s/%s", user.getName(), key);
        metaDataService.deleteObjectMetaInfo(key, user);

        return awsS3Service.deleteObject(formatKey);
    }

    @Override
    public ObjectMetaInfo createFolder(String folderName, String parentFolderName, User user) {
        ObjectMetaInfo parentFolder = metaDataService.getObjectMetaInfo(parentFolderName, user);
        return metaDataService.createFolder(folderName, parentFolder, user);
    }

    public ObjectMetaInfo renameObject(String key, String newKey, User user) {

        if (key.equals("/") || newKey.equals("/"))
            throw new RootRenameRejectionException();

        String keyS3 = String.format("%s/%s", user.getName(), key);
        String keyDB = key;

        String newKeyS3 = String.format("%s/%s", user.getName(), newKey);
        String newKeyDB = newKey;
        ObjectMetaInfo objectMetaInfo = metaDataService.renameFile(keyDB, newKeyDB, user);
        awsS3Service.renameObject(keyS3, newKeyS3);
        return objectMetaInfo;
    }
}
