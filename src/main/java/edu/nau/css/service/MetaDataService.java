package edu.nau.css.service;

import edu.nau.css.domain.ObjectMetaInfo;
import edu.nau.css.domain.User;

import java.util.List;

public interface MetaDataService {

    void checkObjectExistence(String key, User user);

    void checkObjectsExistence(List<String> keys, User user);

    void checkObjectExistenceAndIsFolder(String key, User user);

    void checkObjectsExistenceAndIsFolder(List<String> destinationKeys, User user);

    ObjectMetaInfo getObjectMetaInfo(String path, User user);

    List<ObjectMetaInfo> getObjectsMetaInfo(List<String> sourceKeys, User user);

    ObjectMetaInfo saveObject(String key, ObjectMetaInfo parent, String fileType, User user);

    ObjectMetaInfo saveObject(ObjectMetaInfo newObjectMetaInfo);

    ObjectMetaInfo createFolder(String folderName, ObjectMetaInfo folderParent, User user);

    ObjectMetaInfo createRootFolder(User newUser);

    void updateObjectLocationObjects(List<ObjectMetaInfo> objectsMetaInfo, List<String> destinationKeys);

    ObjectMetaInfo renameFile(String keyDB, String newKeyDB, User user);

    void deleteObjectMetaInfo(String key, User user);

}
