package edu.nau.css.service.impl;

import edu.nau.css.domain.ObjectMetaInfo;
import edu.nau.css.domain.User;
import edu.nau.css.exception.ObjectIsNotFolderException;
import edu.nau.css.exception.ObjectMetaDataNotFoundException;
import edu.nau.css.repository.ObjectRepository;
import edu.nau.css.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MetaDataServiceImpl implements MetaDataService {

    @Autowired
    private ObjectRepository objectRepository;

    @Override
    public ObjectMetaInfo getObjectMetaInfo(String path, User user) {
        return objectRepository.findByObjectNameAndUser(path, user)
                .orElseThrow(() -> new ObjectMetaDataNotFoundException("Key doesn't exist", Collections.singletonList(path)));
    }

    @Override
    public void checkObjectExistence(String key, User user) {
        if (objectRepository.existsObjectByObjectNameAndUser(key, user))
            return;

        throw new ObjectMetaDataNotFoundException("Key doesn't exist", Collections.singletonList(key));
    }

    @Override
    public void checkObjectsExistence(List<String> keys, User user) {
        List<String> errorKeys = new ArrayList<>();
        for (String key : keys) {
            try {
                checkObjectExistence(key, user);
            } catch (ObjectMetaDataNotFoundException exception) {
                errorKeys.add(key);
            }
        }

        if (!errorKeys.isEmpty()) {
            throw new ObjectMetaDataNotFoundException("Wrong keys", errorKeys);
        }
    }

    @Override
    public void deleteObjectMetaInfo(String key, User user) {
        objectRepository.deleteByObjectNameAndUser(key, user);
    }

    @Override
    public ObjectMetaInfo saveObject(String key, ObjectMetaInfo parent, String fileType, User user) {

        ObjectMetaInfo newObjectMetaInfo = ObjectMetaInfo.builder()
                .withObjectName(key)
                .withParentObject(parent)
                .withFileType(fileType)
                .withIsFolder(Boolean.FALSE)
                .withCreatedAt(LocalDateTime.now())
                .withUpdatedAt(LocalDateTime.now())
                .withUser(user)
                .build();
        return objectRepository.save(newObjectMetaInfo);
    }

    @Override
    public List<ObjectMetaInfo> getObjectsMetaInfo(List<String> sourceObjectNames, User user) {
        return objectRepository.findByObjectNameInAndUser(sourceObjectNames, user);
    }

    @Override
    public void updateObjectLocationObjects(List<ObjectMetaInfo> objectsMetaInfo, List<String> destinationKeys) {

    }

    @Override
    public ObjectMetaInfo saveObject(ObjectMetaInfo newObjectMetaInfo) {
        return objectRepository.save(newObjectMetaInfo);
    }

    @Override
    public void checkObjectsExistenceAndIsFolder(List<String> destinationKeys, User user) throws ObjectMetaDataNotFoundException {
        List<String> errorKeys = new ArrayList<>();
        for (String destinationKey : destinationKeys) {
            try {
                checkObjectExistenceAndIsFolder(destinationKey, user);
            } catch (ObjectIsNotFolderException exception) {
                errorKeys.add(destinationKey);
            }
        }

        if (!errorKeys.isEmpty()) {
            throw new ObjectMetaDataNotFoundException("Wrong keys", errorKeys);
        }
    }

    @Override
    public void checkObjectExistenceAndIsFolder(String key, User user) throws ObjectIsNotFolderException {
        if (objectRepository.existsObjectByObjectNameAndUserAndIsFolder(key, user, Boolean.TRUE))
            return;

        throw new ObjectIsNotFolderException("Object is not folder", Collections.singletonList(key));
    }

    @Override
    public ObjectMetaInfo renameFile(String keyDB, String newKeyDB, User user) {

        ObjectMetaInfo objectMetaInfo = objectRepository.findByObjectNameAndUser(keyDB, user)
                .orElseThrow(ObjectMetaDataNotFoundException::new);

        objectMetaInfo.setObjectName(newKeyDB);
        objectMetaInfo.setUpdatedAt(LocalDateTime.now());

        return objectRepository.save(objectMetaInfo);
    }

    @Override
    public ObjectMetaInfo createFolder(String folderName, ObjectMetaInfo parentFolder, User user) {

        ObjectMetaInfo folder = ObjectMetaInfo.builder()
                .withObjectName(folderName)
                .withParentObject(parentFolder)
                .withIsFolder(Boolean.TRUE)
                .withCreatedAt(LocalDateTime.now())
                .withUpdatedAt(LocalDateTime.now())
                .withUser(user)
                .build();

        return objectRepository.save(folder);
    }

    @Override
    public ObjectMetaInfo createRootFolder(User user) {

        ObjectMetaInfo rootFolder = ObjectMetaInfo.builder()
                .withObjectName("/")
                .withParentObject(null)
                .withIsFolder(Boolean.TRUE)
                .withCreatedAt(LocalDateTime.now())
                .withUpdatedAt(LocalDateTime.now())
                .withUser(user)
                .build();

        return objectRepository.save(rootFolder);
    }
}
