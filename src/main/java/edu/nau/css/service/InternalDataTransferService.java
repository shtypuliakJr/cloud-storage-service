package edu.nau.css.service;

import edu.nau.css.domain.ObjectMetaInfo;
import edu.nau.css.domain.User;
import edu.nau.css.dto.param.PairSourceDestinationDTO;

import java.util.List;

public interface InternalDataTransferService {

    void moveObjectsInside(List<PairSourceDestinationDTO> sourceDestinationList);

    void copyObjectsInside(List<PairSourceDestinationDTO> sourceDestinationList, User user);

    List<String> deleteObject(String key, User user);

    ObjectMetaInfo createFolder(String folderName, String parentFolderName, User user);

}
