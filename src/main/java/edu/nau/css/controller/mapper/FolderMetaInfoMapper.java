package edu.nau.css.controller.mapper;

import edu.nau.css.domain.ObjectMetaInfo;
import edu.nau.css.dto.meta.FolderMetaInfoDTO;
import edu.nau.css.dto.meta.MetaInfoDTO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FolderMetaInfoMapper {

    public static MetaInfoDTO mapObjectMetaInfoToFolderMetaInfoDTO(ObjectMetaInfo objectMetaInfo) {

        List<ObjectMetaInfo> childrenObjects = objectMetaInfo.getChildrenObjects();
        List<MetaInfoDTO> childrenObjectDTOs = Collections.emptyList();

        if (childrenObjects != null) {
            childrenObjectDTOs = childrenObjects.stream()
                    .filter(Objects::nonNull)
                    .map(FileMetaInfoMapper::mapObjectMetaInfoToFileMetaInfoDTO)
                    .collect(Collectors.toList());
        }
        return FolderMetaInfoDTO.builder()
                .withObjectId(objectMetaInfo.getObjectId())
                .withObjectName(objectMetaInfo.getObjectName())
                .withObjectFullName(objectMetaInfo.getObjectName())
                .withParentObjectId(objectMetaInfo.getParentObject() != null ? objectMetaInfo.getParentObject().getObjectId() : null)
                .withIsFolder(objectMetaInfo.getIsFolder())
                .withCreatedAt(objectMetaInfo.getCreatedAt())
                .withUpdatedAt(objectMetaInfo.getUpdatedAt())
                .withUserId(objectMetaInfo.getUser().getUserId())
                .withObjectMetaInfoDTOs(childrenObjectDTOs)
                .build();

    }

}
